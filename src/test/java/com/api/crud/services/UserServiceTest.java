package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        users.add(new UserModel());
        when(userRepository.findAll()).thenReturn(users);

        ArrayList<UserModel> result = userService.getUsers();
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser() {
        UserModel user = new UserModel();
        when(userRepository.save(user)).thenReturn(user);

        UserModel result = userService.saveUser(user);
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        UserModel user = new UserModel();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserModel> result = userService.getUserById(userId);
        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateById() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setFirstName("OldName");
        user.setLastName("OldLastName");
        user.setEmail("oldemail@example.com");

        UserModel request = new UserModel();
        request.setFirstName("NewName");
        request.setLastName("NewLastName");
        request.setEmail("newemail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        UserModel result = userService.updateById(request, userId);

        assertEquals("NewName", result.getFirstName());
        assertEquals("NewLastName", result.getLastName());
        assertEquals("newemail@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        Boolean result = userService.deleteUser(userId);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }
}

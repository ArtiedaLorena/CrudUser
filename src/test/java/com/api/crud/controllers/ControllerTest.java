package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserModel user1;
    private UserModel user2;

    @BeforeEach
    void setUp() {
        user1 = new UserModel();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");

        user2 = new UserModel();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
    }

    @Test
    public void testGetUsers() throws Exception {


        List<UserModel> users = Arrays.asList(user1, user2);
        Mockito.when(userService.getUsers()).thenReturn(new ArrayList<>(users));


        mockMvc.perform(get("/user/getusers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].email", is("jane.doe@example.com")));
    }

    @Test
    public void testSaveUser() throws Exception {
        Mockito.when(userService.saveUser(any(UserModel.class))).thenReturn(user1);

        mockMvc.perform(post("/user/saveuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(user1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user1.getLastName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(user1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user1.getLastName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    public void testUpdateUserById() throws Exception {
        Mockito.when(userService.updateById(any(UserModel.class), eq(1L))).thenReturn(user1);

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(user1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user1.getLastName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    public void testDeleteById() throws Exception {
        Mockito.when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 1 deleted"));

        Mockito.when(userService.deleteUser(2L)).thenReturn(false);

        mockMvc.perform(delete("/user/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 2 not found"));
    }
}

package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getusers")
    public ArrayList<UserModel> getUsers(){
        return this.userService.getUsers();
    }

    @PostMapping("saveuser")
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }

    @GetMapping(path= "/{id}")
    public Optional <UserModel> getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @PutMapping
    public UserModel updateUserById(@RequestBody UserModel request, @PathVariable Long id){
        return this.userService.updateById(request, id);
    }
    @DeleteMapping(path="/{id}")
    public String deleteById(@PathVariable Long id){
        boolean ok= this.userService.deleteUser(id);
        if(ok){
            return "User with id "+ id + " deleted";
        }
        else{
            return "User with id " + id + " not found";
        }
    }



}

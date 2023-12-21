package com.cliche.cliche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.cliche.cliche.domain.user.User;
import com.cliche.cliche.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @MutationMapping
    public User createUser(@Argument("email") String email, @Argument("password") String password) {
        return userService.createUser(new User(email, password));
    }

    @QueryMapping
    public User userById(@Argument("id") String id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public User userByEmail(@Argument("email") String email) {
        return userService.getUserByEmail(email);
    }

    @MutationMapping
    public User updateUser(@Argument("id") String id, @Argument("email") String email,
            @Argument("password") String password) {
        return userService.updateUser(id, new User(email, password));
    }

    @MutationMapping
    public void deleteUser(@Argument("id") String id) {
        userService.deleteUser(id);
    }

}
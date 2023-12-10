package com.main.controller;

import com.main.model.User;
import com.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    //inject the service
    @Autowired
    private UserService userService;

    @PostMapping("/saveUserCart")
    public User saveUser(@RequestBody User user) {
         return userService.saveUser(user);
    }

}

package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // for generating user unique id you can write code her or write code in service class
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //get single user
    @GetMapping("/{userId}")//value is getting dynamically because of this we use {} curly braces
    @CircuitBreaker(name = "RatingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
       // return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    /*
    *
     creating fall back method for circuit breaker
     Note: Fall back method and its parent method return type and number of and type of parameter must be same
     and fallbackMethod name must be same to circuit breaker handling method name below method please see
     after that you need to configure the circuit breaker there are two types to configure the circuit breaker
     * 1 using java base
     * 2 using yml base of pur configuration in .properties file
    *
     */
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
        logger.info("The FallBack method is executed because service is down: {}" ,ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This Dummy user is created because some services down")
                .userId("12345")
                .build();
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    //get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
       List<User> allUser = userService.getAllUser();
       return ResponseEntity.ok(allUser);
    }

}

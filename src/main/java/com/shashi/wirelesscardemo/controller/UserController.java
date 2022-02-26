package com.shashi.wirelesscardemo.controller;

import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;

import com.shashi.wirelesscardemo.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //The function receives a POST request, processes it, creates a new user
    @PostMapping(value = "/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user)  {
        LOG.info("request received for create  is :{}", user );
        UserResponse userResponse = userService.createUser(user);
        LOG.info(" create  user request is:{} response  is :{}", user, userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }


    //The function receives a GET request, processes it and gives back a list of
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.findAllUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    //The function receives a GET request with id in the url path, processes it and returns
    @GetMapping({"/{id}"})
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    //The function receives a PUT request, updates the User with the  Id and returns the updated user
    @PutMapping({"/{emailId}"})
    public ResponseEntity<User> updateUser(@PathVariable("emailId") String emailId, @RequestBody User user) {
        userService.updateUser(emailId, user);
        return new ResponseEntity<>(userService.getUserById(emailId), HttpStatus.OK);
    }
    //The function receives a DELETE request, deletes the User with the specified Id.
    @DeleteMapping({"/{emailId}"})
    public ResponseEntity<User> deleteUser(@PathVariable("emailId") String emailId) {
        userService.deleteUser(emailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

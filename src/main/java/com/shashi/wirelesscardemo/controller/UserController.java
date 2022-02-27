package com.shashi.wirelesscardemo.controller;

import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;

import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.services.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Api(tags = {"User"})
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //The function receives a POST request, processes it, creates a new user
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDto userDto) {
        LOG.info("request received for create  is :{}", userDto);
        UserResponse userResponse = userService.createUser(userDto);
        LOG.info(" create  user request is:{} response  is :{}", userDto, userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    //The function receives a DELETE request, deletes the User with the specified Id.
    @DeleteMapping({"/{emailId}"})
    @ApiOperation(value = "Delete User", nickname = "delete user by email", notes = "Delete user by email")
    public ResponseEntity<User> deleteUser(@PathVariable("emailId") String emailId) {
        LOG.info("request received for delete user for email  is :{}", emailId);
        userService.deleteUser(emailId);
        LOG.info("user deleted for email:{}", emailId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //The function receives a GET request, processes it and gives back a list of
    @GetMapping(value = "/search")
    public ResponseEntity<List<User>> getSearch(@Nullable @RequestParam String firstName, @Nullable @RequestParam String email, @Nullable @RequestParam Integer age) {
        List<User> userList = userService.search(firstName, email, age);
        System.out.println(userList.size());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}

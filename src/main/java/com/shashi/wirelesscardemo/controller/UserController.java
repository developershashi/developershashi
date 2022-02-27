package com.shashi.wirelesscardemo.controller;

import com.shashi.wirelesscardemo.enums.Gender;
import com.shashi.wirelesscardemo.models.DeleteRequest;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;

import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.pojo.UserRequestDto;
import com.shashi.wirelesscardemo.services.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Locale;

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
 /*   @DeleteMapping
    @ApiOperation(value = "Delete User", nickname = "delete user by email", notes = "Delete user by email")
    public ResponseEntity<User> deleteUser(@Valid @RequestBody UserDto userDto) {
        LOG.info("request received for delete user for email  is :{}", userDto);
        userService.deleteUserByEmail(userDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
    //The function receives a DELETE request, deletes the User with the specified Id.
    @DeleteMapping
    @ApiOperation(value = "Delete User", nickname = "delete user by email", notes = "Delete user by email")
    public ResponseEntity<UserResponse> deleteUser2(@Valid @RequestBody DeleteRequest deleteRequest) {
        LOG.info("request received for delete user :{}", deleteRequest);
        UserResponse userResponse = userService.deleteUserByRequest(deleteRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.NO_CONTENT);
    }/*  //The function receives a DELETE request, deletes the User with the specified Id.
    @DeleteMapping({"/{emailId}"})
    @ApiOperation(value = "Delete User", nickname = "delete user by email", notes = "Delete user by email")
    public ResponseEntity<User> deleteUser(@PathVariable("emailId") String emailId) {
        LOG.info("request received for delete user for email  is :{}", emailId);
        userService.deleteUser(emailId);
        LOG.info("user deleted for email:{}", emailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    //The function receives a GET request, processes it and gives back a list of
    @GetMapping(value = "/search")
    public ResponseEntity<?> getSearch(@Nullable @RequestParam String firstName,
                                                @Nullable @RequestParam String gender,
                                                @Nullable @RequestParam Integer age,
                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "firstName") String sortBy) {

        UserRequestDto dto = new UserRequestDto();
        if (!StringUtils.isEmpty(firstName)) {
            dto.setFirstName(firstName);
        }
        if (!StringUtils.isEmpty(gender)) {
            dto.setGender(gender);
        }
        if (!StringUtils.isEmpty(age)) {
            dto.setAge(age);
        }

        dto.setPageNo(pageNo);
        dto.setPageSize(pageSize);
        dto.setSortBy(sortBy);

        Page<User> userList = userService.search(dto);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}

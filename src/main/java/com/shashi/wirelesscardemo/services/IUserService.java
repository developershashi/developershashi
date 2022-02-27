package com.shashi.wirelesscardemo.services;

import com.shashi.wirelesscardemo.enums.Gender;
import com.shashi.wirelesscardemo.models.DeleteRequest;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.pojo.UserDto;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserDto user);
    UserResponse deleteUserByEmail(UserDto user);
    UserResponse deleteUserByRequest(DeleteRequest deleteRequest);

    List<User> search(String firstName, String gender , Integer age);

    void deleteUser(String emailId);
}
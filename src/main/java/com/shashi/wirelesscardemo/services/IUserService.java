package com.shashi.wirelesscardemo.services;

import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.pojo.UserDto;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserDto user);

    List<User> search( String firstName,  String email,  Integer age);

    void deleteUser(String emailId);
}
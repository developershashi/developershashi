package com.shashi.wirelesscardemo.services;

import com.shashi.wirelesscardemo.enums.Gender;
import com.shashi.wirelesscardemo.models.DeleteRequest;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.pojo.UserRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserDto user);
    UserResponse deleteUserByEmail(UserDto user);
    UserResponse deleteUserByRequest(DeleteRequest deleteRequest);

    Page<User> search(UserRequestDto dto);

    void deleteUser(String emailId);
}
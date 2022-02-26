package com.shashi.wirelesscardemo.services;

import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse createUser(User user);

    List<User> findAllUser();

    User getUserById(String id);

    void updateUser(String id, User user);

    void deleteUser(String emailId);
}
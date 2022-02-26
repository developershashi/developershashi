package com.shashi.wirelesscardemo.services.impl;

import com.shashi.wirelesscardemo.enums.CommonState;
import com.shashi.wirelesscardemo.exceptions.UserServiceException;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.repository.UserRepository;
import com.shashi.wirelesscardemo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponse createUser(User user) {

            if (user != null) {
                User saveUser = userRepository.save(user);
                UserResponse userResponse = new UserResponse( saveUser,CommonState.USER_CREATED.getMessage(), HttpStatus.CREATED, HttpStatus.CREATED.value() );
                return userResponse;
            } else {
                throw new UserServiceException("user data not found !!");
            }
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }


    @Override
    public void updateUser(String emailId, User user) {
        User userfromDb = userRepository.findById(emailId).get();
        System.out.println(userfromDb.toString());
      //todo Mapstruct
        userRepository.save(userfromDb);
    }
    @Override
    public void deleteUser(String emailid) {
            userRepository.deleteById(emailid);
        }

    }


package com.shashi.wirelesscardemo.services.impl;

import com.shashi.wirelesscardemo.enums.CommonState;
import com.shashi.wirelesscardemo.exceptions.UserServiceException;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.repository.UserRepository;
import com.shashi.wirelesscardemo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            UserResponse userResponse = new UserResponse(saveUser, CommonState.USER_CREATED.getMessage(), HttpStatus.CREATED, HttpStatus.CREATED.value());
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
    public List<User> search(String firstName, String email, int age) {

        List<User> list = null;
        UserDto dto = new UserDto();

        if (!StringUtils.isEmpty(firstName)) {
            dto.setFirstName(firstName);
            list = userRepository.findAll(getSpecification(dto));
        } else if (!StringUtils.isEmpty(email)) {
            dto.setEmail(email);
            list = userRepository.findAll(getSpecification(dto));
        } else if (!StringUtils.isEmpty(age)) {
            dto.setAge(age);
            list = userRepository.findAll(getSpecification(dto));
        }

        return list;
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


    private Specification<User> getSpecification(UserDto request) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (!StringUtils.isEmpty(request.getFirstName())) {
                predicates.add(builder.and(builder.like(root.get("firstName"), "%" + request.getFirstName() + "%")));
            }

            if (!StringUtils.isEmpty(request.getEmail())) {
                predicates.add(builder.and(builder.like(root.get("email"), "%" + request.getEmail() + "%")));

            }

            if (!StringUtils.isEmpty(request.getAge())) {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                Date date = format.parse(myString);

                predicates.add(builder.and(builder.greaterThan(root.get("birthday"), request.getAge())));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}


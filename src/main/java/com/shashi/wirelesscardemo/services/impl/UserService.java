package com.shashi.wirelesscardemo.services.impl;

import com.shashi.wirelesscardemo.enums.CommonState;
import com.shashi.wirelesscardemo.enums.Gender;
import com.shashi.wirelesscardemo.exceptions.UserServiceException;
import com.shashi.wirelesscardemo.mapper.UserMapper;
import com.shashi.wirelesscardemo.models.DeleteRequest;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.models.UserResponse;
import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.pojo.UserRequestDto;
import com.shashi.wirelesscardemo.repositories.UserRepository;
import com.shashi.wirelesscardemo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * This methos is used for creating a new user in db
     * @param userDto
     * @return userResponse
     */
    @Override
    public UserResponse createUser(UserDto userDto) {

        if (userDto != null) {
            User user = UserMapper.INSTANCE.dtoToEntityMapper(userDto);
            User saveUser = userRepository.save(user);
            UserDto userDtoResponse = UserMapper.INSTANCE.entityToDtoMapper(saveUser);
            UserResponse userResponse = new UserResponse(userDtoResponse, CommonState.USER_CREATED.getMessage(), HttpStatus.CREATED, HttpStatus.CREATED.value());
            return userResponse;
        } else {
            throw new UserServiceException("user data not found !!");
        }
    }

    @Override
    public void deleteUser(String emailid) {
        userRepository.deleteById(emailid);
    }

    @Override
    public UserResponse deleteUserByEmail(UserDto userDto) {
        if (userDto != null ) {
            userRepository.deleteById(userDto.getEmail());
            UserResponse userResponse = new UserResponse(null, CommonState.USER_CREATED.getMessage(), HttpStatus.CREATED, HttpStatus.CREATED.value());
            return userResponse;
        } else {
            throw new UserServiceException("user data not found !!");
        }
    }
    @Override
    public UserResponse deleteUserByRequest(DeleteRequest deleteRequest) {
        List<User> getDbUser = null;
        if (deleteRequest != null ) {
            if(!deleteRequest.getRecords().isEmpty()&& deleteRequest.getRecords().containsKey("email")){
                List<String> emailList= deleteRequest.getRecords().get("email");
                getDbUser=  userRepository.findAllById(emailList);
                List<String> collect = getDbUser.stream().map(User::getEmail).collect(Collectors.toList());
                if(!collect.isEmpty()) {
                    userRepository.deleteAllById(collect);
                }else{
                    throw new UserServiceException("no record found in db!");
                }

                UserResponse   userResponse=   new UserResponse(getDbUser, CommonState.USER_DELETED.getMessage(), HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value());
                return userResponse;

            }
            else{
                throw new UserServiceException("Id not found in request !!");
            }
        } else {
            throw new UserServiceException("Id not found in request !!");
        }
    }

    @Override
    public Page<User> search(UserRequestDto dto) {

//        List<User> list = null;
        validator(dto);
        Pageable paging = PageRequest.of(dto.getPageNo(), dto.getPageSize(), Sort.by(dto.getSortBy()));
        Page<User> all = userRepository.findAll(getSpecification(dto), paging);
        return all;
    }

    private void validator(UserRequestDto userDto) {
        if (StringUtils.isEmpty(userDto.getFirstName()) && StringUtils.isEmpty(userDto.getGender()) &&StringUtils.isEmpty(userDto.getAge())) {
            throw new UserServiceException("Anyone field is mandatory");
        }

        if (!StringUtils.isEmpty(userDto.getAge())) {
            if (userDto.getAge() < 25 || userDto.getAge() > 55) {
                throw new UserServiceException("Age not in range");
            }
        }


    }

    private Specification<User> getSpecification(UserRequestDto request) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(request.getFirstName())) {
                predicates.add(builder.and(builder.like(builder.upper(root.get("firstName")), "%" + request.getFirstName().toUpperCase(Locale.ROOT) + "%")));
            }

//            if (!StringUtils.isEmpty(request.getEmail())) {
//                predicates.add(builder.and(builder.like(root.get("email"), "%" + request.getEmail() + "%")));
//            }

            if (!StringUtils.isEmpty(request.getGender())) {
                predicates.add(builder.and(builder.equal(builder.upper(root.get("gender")), request.getGender().toUpperCase(Locale.ROOT))));
            }

            if (!StringUtils.isEmpty(request.getAge())) {
                String dateFormat = "dd.MM.yyyy";

//                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                LocalDate localDate = LocalDate.now().minusYears(request.getAge());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                String outformat = formatter.format(localDate);
                try {
                    Date date1 = new SimpleDateFormat(dateFormat).parse(outformat);
                    predicates.add(builder.and(builder.greaterThan(root.get("birthday"), date1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(localDate);
//                DateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
//                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                Date date = format.parse(myString);

            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}


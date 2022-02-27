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
                if(!getDbUser.isEmpty()) {
                    userRepository.deleteAllById(emailList);
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
    public List<User> search(String firstName, String gender, Integer age) {

        List<User> list = null;
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

        validator(dto);

        list = userRepository.findAll(getSpecification(dto));

        return list;
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


package com.shashi.wirelesscardemo.utility;

import com.google.gson.Gson;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.pojo.UserDto;
import com.shashi.wirelesscardemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utility {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void fillData() throws IOException {

        Resource resource = new ClassPathResource("db.json");
        FileInputStream file = new FileInputStream(resource.getFile());
        Reader reader = new InputStreamReader(file, "UTF-8");
        UserDto[] result = new Gson().fromJson(reader, UserDto[].class);
        fillDataInDB(result);
        System.out.println(Arrays.asList(result));
    }

    private void fillDataInDB(UserDto[] users) {

        List<User> collect = Arrays.stream(users).map(o -> {
            try {
                Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(o.getBirthday());
                return new User(o.getFirstName(), o.getLastName(), o.getEmail(), o.getGender(), date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        List<User> users1 = userRepository.saveAll(collect);
        System.out.println("users1 count -" + users1.size());
    }
}

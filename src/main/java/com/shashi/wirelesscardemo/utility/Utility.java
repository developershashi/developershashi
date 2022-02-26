package com.shashi.wirelesscardemo.utility;

import com.google.gson.Gson;
import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class Utility {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void fillData() throws IOException {

        Resource resource = new ClassPathResource("db.json");
        FileInputStream file = new FileInputStream(resource.getFile());
        Reader reader = new InputStreamReader(file, "UTF-8");
        User[] result = new Gson().fromJson(reader, User[].class);
        fillDataInDB(result);
        System.out.println(Arrays.asList(result));
    }

    private void fillDataInDB(User[] users){
        List<User> users1 = userRepository.saveAll(Arrays.asList(users));
        System.out.println("users1 count -"+users1.size());
    }
}

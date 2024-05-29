package com.example.tierpark;

import com.example.tierpark.entities.Feed;
import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.FeedService;
import com.example.tierpark.services.impl.UserService;

import java.sql.Date;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.insert(
                User.builder()
                        .name("Kubanychbek")
                        .lastname("Kushtarbekov")
                        .login("kuba")
                        .birthDate(new Date(2002, 3, 6))
                        .genderId(1)
                        .roleId(3)
                        .password("1234")
                        .build()
        );
    }
}

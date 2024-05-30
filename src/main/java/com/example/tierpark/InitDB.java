package com.example.tierpark;

import com.example.tierpark.entities.User;
import com.example.tierpark.services.impl.UserService;

import java.sql.Date;

public class InitDB {
    public static void main(String[] args) {
        createAdmin();
    }

    private static void createAdmin(){
        UserService userService = new UserService();
        userService.insert(
                User.builder()
                        .name("buba")
                        .lastname("buba")
                        .login("buba")
                        .birthDate(Date.valueOf("2002-06-03"))
                        .genderId(1)
                        .roleId(1)
                        .password("buba")
                        .build()
        );
    }
}

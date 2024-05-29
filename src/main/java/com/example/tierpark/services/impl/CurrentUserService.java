package com.example.tierpark.services.impl;

import com.example.tierpark.entities.User;

public class CurrentUserService {
    private static User CURRENT_USER;

    public static void setCurrentUser(User user){
        CURRENT_USER = user;
    }

    public static User getCurrentUser(){
        return CURRENT_USER;
    }
}

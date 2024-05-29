package com.example.tierpark.util;

import com.example.tierpark.entities.User;

public class CurrentUser {
    private static User user;

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    public static User getUser() {
        return user;
    }
}

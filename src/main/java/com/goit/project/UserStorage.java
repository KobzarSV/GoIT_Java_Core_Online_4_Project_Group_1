package com.goit.project;

import java.util.ArrayList;

public class UserStorage {
    private static ArrayList<User> userSettings;

    public UserStorage() {
        userSettings = new ArrayList<>();
    }

    public static void add(User user) {
        userSettings.add(user);
    }

    public static User get(int userId) {
        User result = null;
        for (User user : userSettings) {
            if (user.getId() == userId) {
                result = user;
                break;
            }
        }
        return result;
    }

}

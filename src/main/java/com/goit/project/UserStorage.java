package com.goit.project;

import java.util.HashMap;

public class UserStorage {
    public static HashMap<Integer, User> userSettings;

    public UserStorage() {
        userSettings = new HashMap<>();
    }

    public static void add(User user) {
        userSettings.put(user.getId(), user);
    }

    public static User get(int userId) {
        return userSettings.get(userId);
    }


}

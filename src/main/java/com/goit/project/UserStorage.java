package com.goit.project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorage {
    private static volatile UserStorage instance; // Singleton
    private ConcurrentHashMap<Integer, User> userSettings;

    private UserStorage() {
        userSettings = new ConcurrentHashMap<>();
    }

    public static UserStorage getInstance() { //«блокировка с двойной проверкой» (Double-Checked Locking)
        UserStorage result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserStorage.class) {
            if (instance == null) {
                instance = new UserStorage();
            }
            return instance;
        }
    }

    public void add(User user) {
        userSettings.put(user.getId(), user);
    }

    public User get(int userId) {
        return userSettings.get(userId);
    }

    public List<Integer> getUsersIdWithPresentScheduler(int time) {
        List<Integer> result = new ArrayList<>();
        for (User user : userSettings.values()) {
            if (user.getSchedulerTime() == time) {
                result.add(user.getId());
            }
        }
        return result;
    }

}

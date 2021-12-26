package com.goit.project;

import java.util.List;

public class UserService {
    UserStorage userStorage = UserStorage.getInstance();

    public void createUser(int userId) {
        userStorage.add(new User(userId));
    }

    public void setBank(int userId, String bank) {
        userStorage.get(userId).setBank(bank);
    }

    public void setRounding(int userId, int rounding) {
        userStorage.get(userId).setRounding(rounding);
    }

    public void setUsd(int userId, boolean usd) {
        userStorage.get(userId).setUsd(usd);
    }

    public void setEur(int userId, boolean eur) {
        userStorage.get(userId).setEur(eur);
    }

    public void setRub(int userId, boolean rub) {
        userStorage.get(userId).setRub(rub);
    }

    public void setScheduler(int userId, boolean scheduler) {
        userStorage.get(userId).setScheduler(scheduler);
    }

    public void setSchedulerTime(int userId, int time) {
        userStorage.get(userId).setSchedulerTime(time);
    }

    public String getBank(int userId) {
        return userStorage.get(userId).getBank();
    }

    public Integer getRounding(int userId) {
        return userStorage.get(userId).getRounding();
    }

    public boolean[] getCurrencies(int userId) {
        return new boolean[] {userStorage.get(userId).isUsd(), userStorage.get(userId).isEur(), userStorage.get(userId).isRub()};
    }

    public boolean getScheduler(int userId) {
        return userStorage.get(userId).isScheduler();
    }

    public Integer getSchedulerTime(int userId) {
        return userStorage.get(userId).getSchedulerTime();
    }

    public List<Integer> getUsersIdWithPresentScheduler(int time) {
        return userStorage.getUsersIdWithPresentScheduler(time);
    }

}

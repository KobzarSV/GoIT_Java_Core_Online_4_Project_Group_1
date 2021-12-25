package com.goit.project;

public class UserService {

    public void createUser(int userId) {
        UserStorage.add(new User(userId));
    }

    public void setBank(int userId, String bank) {
        UserStorage.get(userId).setBank(bank);
    }

    public void setRounding(int userId, int rounding) {
        UserStorage.get(userId).setRounding(rounding);
    }

    public void setUsd(int userId, boolean usd) {
        UserStorage.get(userId).setUsd(usd);
    }

    public void setEur(int userId, boolean eur) {
        UserStorage.get(userId).setEur(eur);
    }

    public void setRub(int userId, boolean rub) {
        UserStorage.get(userId).setRub(rub);
    }

    public void setScheduler(int userId, boolean scheduler) {
        UserStorage.get(userId).setScheduler(scheduler);
    }

    public void setSchedulerTime(int userId, int time) {
        UserStorage.get(userId).setSchedulerTime(time);
    }

    public String getBank(int userId) {
        return UserStorage.get(userId).getBank();
    }

    public Integer getRounding(int userId) {
        return UserStorage.get(userId).getRounding();
    }

    public boolean[] getCurrencies(int userId) {
        return new boolean[]{UserStorage.get(userId).isUsd(), UserStorage.get(userId).isEur(), UserStorage.get(userId).isRub()};
    }

    public boolean getScheduler(int userId) {
        return UserStorage.get(userId).isScheduler();
    }

    public Integer getSchedulerTime(int userId) {
        return UserStorage.get(userId).getSchedulerTime();
    }

}

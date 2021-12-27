package com.goit.project;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class UserService {

    private static volatile UserService instance; // Singleton
    private UserStorage userStorage;

    private UserService() {
        userStorage = UserStorage.getInstance();
    }

    public static UserService getInstance() { //«блокировка с двойной проверкой» (Double-Checked Locking)
        UserService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserStorage.class) {
            if (instance == null) {
                instance = new UserService();
            }
            return instance;
        }
    }

    public void createUser(int userId) {
        userStorage.add(new User(userId));
    }

    public User getUser(int userId) {
        return userStorage.get(userId);
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

    public int getRounding(int userId) {
        return userStorage.get(userId).getRounding();
    }

    public boolean getUsd(int userId) {
        return userStorage.get(userId).isUsd();
    }

    public boolean getEur(int userId) {
        return userStorage.get(userId).isEur();
    }

    public boolean getRub(int userId) {
        return userStorage.get(userId).isRub();
    }

    public boolean getScheduler(int userId) {
        return userStorage.get(userId).isScheduler();
    }

    public int getSchedulerTime(int userId) {
        return userStorage.get(userId).getSchedulerTime();
    }

    public List<Integer> getUsersIdWithPresentScheduler(int time) {
        return userStorage.getUsersIdWithPresentScheduler(time);
    }

    public String getInfo(int userId) throws Exception {
        String bank = getBank(userId);
        boolean usd = getUsd(userId);
        boolean eur = getEur(userId);
        boolean rub = getRub(userId);
        int rounding = getRounding(userId);
        DataCaching data = DataCaching.getInstance();
        HashMap<String, Currency> currencies = data.getCurrenciesByBank(bank);
        return resultStringForming(bank, usd, eur, rub, rounding, currencies);
    }

    private String resultStringForming(String bank, boolean usd, boolean eur, boolean rub, int rounding,
                                       HashMap<String, Currency> currencies) throws Exception {
        StringBuilder result = new StringBuilder();
        String bankString;
        switch (bank) {
            case ("NBU"):
                bankString = "НБУ";
                break;
            case ("Mono"):
                bankString = "МоноБанк";
                break;
            case ("PB"):
                bankString = "ПриватБанк";
                break;
            default:
                throw new Exception("Invalid bank name '" + bank + "', must be one of NBU, PB, Mono.");
        }
        if (!usd && !eur && !rub) {
            result.append("Не выбрана валюта оповещения\n");
        } else {
            result.append("Курс валют в ")
                    .append(bankString)
                    .append(":\n\n");
        }
        if (usd) {
            result.append("USD/UAH\nПокупка: ");
            result.append(currencies.get("USD").getRateBuy().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\nПродажа: ");
            result.append(currencies.get("USD").getRateSell().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\n\n");
        }
        if (eur) {
            result.append("EUR/UAH\nПокупка: ");
            result.append(currencies.get("EUR").getRateBuy().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\nПродажа: ");
            result.append(currencies.get("EUR").getRateSell().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\n\n");
        }
        if (rub) {
            result.append("RUB/UAH\nПокупка: ");
            result.append(currencies.get("RUB").getRateBuy().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\nПродажа: ");
            result.append(currencies.get("RUB").getRateSell().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\n\n");
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

}

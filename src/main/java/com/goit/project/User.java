package com.goit.project;

import java.util.Objects;

public class User {
    private boolean active;
    private final int id;
    private String bank;
    private boolean usd;
    private boolean eur;
    private boolean rub;
    private int rounding;
    private boolean scheduler;
    private int schedulerTime;

    public User(int id) {
        this.id = id;
        active = true;
        bank = "NBU";
        usd = true;
        eur = true;
        rub = true;
        rounding = 2;
        scheduler = false;
        schedulerTime = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        if (Objects.equals(bank, "NBU") || Objects.equals(bank, "PB") || Objects.equals(bank, "Mono")) {
            this.bank = bank;
        } else {
            System.out.println("Rejected! Not existing value.");
        }
    }

    public boolean isUsd() {
        return usd;
    }

    public void setUsd(boolean usd) {
        this.usd = usd;
    }

    public boolean isEur() {
        return eur;
    }

    public void setEur(boolean eur) {
        this.eur = eur;
    }

    public boolean isRub() {
        return rub;
    }

    public void setRub(boolean rub) {
        this.rub = rub;
    }

    public int getRounding() {
        return rounding;
    }

    public void setRounding(int rounding) {
        this.rounding = rounding;
    }

    public boolean isScheduler() {
        return scheduler;
    }

    public void setScheduler(boolean scheduler) {
        this.scheduler = scheduler;
    }

    public int getSchedulerTime() {
        return schedulerTime;
    }

    public void setSchedulerTime(int schedulerTime) {
        this.schedulerTime = schedulerTime;
    }
}

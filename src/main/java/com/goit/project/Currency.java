package com.goit.project;

import java.math.BigDecimal;

class Currency{
    String name; // EUR, USD, RUB
    BigDecimal rateBuy;
    BigDecimal rateSell;
    String bankName; // NBU, PB, Mono
    String baseCName = "UAH"; //Валюта по отношению к которой берется курс. У нас пока только гривна.

    public Currency() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRateBuy(BigDecimal rateBuy) {
        this.rateBuy = rateBuy;
    }

    public void setRateSell(BigDecimal rateSell) {
        this.rateSell = rateSell;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRateBuy() {
        return rateBuy;
    }

    public BigDecimal getRateSell() {
        return rateSell;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBaseCName() {
        return baseCName;
    }
}
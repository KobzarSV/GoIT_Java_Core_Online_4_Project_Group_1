package com.goit.project.banks;

import java.math.BigDecimal;
import java.util.Objects;

public class Monobank implements Bank{
    int currencyCodeA;
    int currencyCodeB = 980;
    long date;
    BigDecimal rateSell;
    BigDecimal rateBuy;
    BigDecimal rateCross;

    public static final String URL_MONO = "https://api.monobank.ua/bank/currency";

    public int getCode() {
        return currencyCodeA;
    }

    @Override
    public BigDecimal getBuy() {
        return rateBuy;
    }

    @Override
    public BigDecimal getSale() {
        return rateSell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monobank monobank = (Monobank) o;
        return currencyCodeA == monobank.currencyCodeA && Objects.equals(rateSell, monobank.rateSell) && Objects.equals(rateBuy, monobank.rateBuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCodeA, rateSell, rateBuy);
    }

    @Override
    public String toString() {
        return "Monobank{" +
                "currencyCodeA=" + currencyCodeA +
                ", rateSell=" + rateSell +
                ", rateBuy=" + rateBuy +
                '}';
    }
}

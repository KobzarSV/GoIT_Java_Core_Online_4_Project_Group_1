package com.goit.project.banks;

import java.math.BigDecimal;
import java.util.Objects;

public class Monobank {
    int currencyCodeA;
    int currencyCodeB = 980;
    long date;
    BigDecimal rateSell;
    BigDecimal rateBuy;
    BigDecimal rateCross;

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public BigDecimal getRateSell() {
        return rateSell;
    }

    public BigDecimal getRateBuy() {
        return rateBuy;
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

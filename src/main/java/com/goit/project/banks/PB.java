package com.goit.project.banks;

import java.math.BigDecimal;
import java.util.Objects;

public class PB implements Bank{
    String ccy;
    String base_ccy;
    BigDecimal buy;
    BigDecimal sale;

    public static final String URL_PB = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    public int getCode(){
        int code = 0;
        switch (ccy){
            case "USD":
                code = 840;
                break;
            case "EUR":
                code = 978;
                break;
            case "RUR":
                code = 643;
                break;
            default:
                code = 0;
        }
        return code;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSale() {
        return sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PB pb = (PB) o;
        return Objects.equals(ccy, pb.ccy) && Objects.equals(buy, pb.buy) && Objects.equals(sale, pb.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccy, buy, sale);
    }

    @Override
    public String toString() {
        return "PB{" +
                "ccy='" + ccy + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}

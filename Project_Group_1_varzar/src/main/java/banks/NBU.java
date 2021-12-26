package banks;

import java.math.BigDecimal;
import java.util.Objects;

public class NBU implements Bank{
    int r030; // currency code
    String txt; // description currency
    BigDecimal rate; //value of currency
    String cc;
    String exchangedate; //date
    private static final String URL_NBU = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final String  [] arrayOfCodesNBU = {"USD", "EUR", "RUB"};
    private static final int  [] arrayOfCodes = {840, 978, 643};

    public int[] getArrayOfCodes(){
        return arrayOfCodes;
    }

    public String getUrl(){
        return URL_NBU;
    }

    public BigDecimal getBuy(){
        return rate;
    }

    public BigDecimal getSale(){
        return rate;
    }

    public int getCode(){
        return  r030;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getCc() {
        return cc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NBU nbu = (NBU) o;
        return Objects.equals(rate, nbu.rate) && Objects.equals(cc, nbu.cc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, cc);
    }

    @Override
    public String toString() {
        return "NBU{" +
                "rate=" + rate +
                ", cc='" + cc + '\'' +
                '}';
    }
}

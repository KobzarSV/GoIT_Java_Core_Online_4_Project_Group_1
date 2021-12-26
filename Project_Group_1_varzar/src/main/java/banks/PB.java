package banks;

import java.math.BigDecimal;
import java.util.Objects;

public class PB {
    String ccy;
    String base_ccy;
    BigDecimal buy;
    BigDecimal sale;

    private static final String  [] arrayOfCodesPB = {"USD", "EUR", "RUR"};


    public int getCode(){
        int code;
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
                throw new IllegalStateException("Unexpected value: " + ccy);
        }
        return code;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public String getCcy() {
        return ccy;
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

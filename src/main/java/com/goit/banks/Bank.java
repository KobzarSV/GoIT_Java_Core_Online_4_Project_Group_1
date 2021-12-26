package com.goit.banks;

import java.math.BigDecimal;

public interface Bank {
    int getCode();
    BigDecimal getBuy();
    BigDecimal getSale();
    String getUrl();
    public int[] getArrayOfCodes();
}

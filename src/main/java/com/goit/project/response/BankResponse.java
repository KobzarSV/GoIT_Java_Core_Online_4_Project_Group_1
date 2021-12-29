package com.goit.project.response;

import com.goit.project.utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class BankResponse {
    ChoiceBank choice;

    public BankResponse(ChoiceBank choice) {
        this.choice = choice;
    }

    public HashMap<String, BigDecimal> getCurrency() throws IOException, InterruptedException {
        HashMap<String, BigDecimal> currency;
        switch (choice){
            case NBU :
                currency = Utils.getCurrencies(ChoiceBank.NBU);
                break;
            case PB:
                currency = Utils.getCurrencies(ChoiceBank.PB);
                break;
            case Monobank:
                currency = Utils.getCurrencies(ChoiceBank.Monobank);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return currency;
    }
}
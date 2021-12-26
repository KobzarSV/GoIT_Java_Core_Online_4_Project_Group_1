package com.goit.task;

import com.goit.utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BankResponse {
    ChoiceBank choice;

    public BankResponse(ChoiceBank choice) {
        this.choice = choice;
    }

    String [] buySale = {"Buy", "Sale"};
    List<String> buy_sale = List.of(new String[]{"Buy", "Sale"});
    String [] cur = {"USD", "EUR", "RUB"};
    List<String> curr = List.of(new String[]{"USD", "EUR", "RUB"});

    public HashMap<String, BigDecimal> getCurrency() throws IOException, InterruptedException {

        HashMap<String, BigDecimal> currency = new HashMap<String, BigDecimal>();


        switch (choice){
            case NBU :
                BigDecimal[][] nbuCurrency = Utils.getNBUcurrency();
                for (int i = 0; i < curr.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(curr.get(i) + "nbu" + buy_sale.get(j), nbuCurrency[i][j]);
                        System.out.println(curr.get(i) + "nbu" + buy_sale.get(j)  + " = " + nbuCurrency[i][j]);
                    }
                }
                break;
            case PB:
                BigDecimal[][] pbCurrency = Utils.getPBcurrency();
                for (int i = 0; i < curr.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(curr.get(i) + "pb" + buy_sale.get(j), pbCurrency[i][j]);
                        System.out.println(curr.get(i) + "pb" + buy_sale.get(j)  + " = " + pbCurrency[i][j]);
                    }
                }
                break;
            case Monobank:
                BigDecimal[][] monoCurrency = Utils.getMonoCurrency();
                for (int i = 0; i < curr.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(curr.get(i) + "mono" + buy_sale.get(j), monoCurrency[i][j]);
                        System.out.println(curr.get(i) + "mono" + buy_sale.get(j)  + " = " + monoCurrency[i][j]);
                    }
                }
//                Arrays.asList(monoCurrency).stream()
//                        .filter(mono -> {
//                            new StringBuilder().append(curr::get).toString().append("mono").append(buy_sale::get).toString()
//                        })
//                        .collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return currency;
    }
}
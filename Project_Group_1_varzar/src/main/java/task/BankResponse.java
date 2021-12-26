package task;

import utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class BankResponse {
    ChoiceBank choice;

    public BankResponse(ChoiceBank choice) {
        this.choice = choice;
    }

    String [] buySale = {"Buy", "Sale"};
    List<String> buy_sale = List.of(new String[]{"Buy", "Sale"});
    String [] cur = {"USD", "EUR", "RUB"};
    List<String> currency = List.of(new String[]{"USD", "EUR", "RUB"});

    HashMap<String, BigDecimal> getCurrency() throws IOException, InterruptedException {

        HashMap<String, BigDecimal> currency = new HashMap<String, BigDecimal>();


        switch (choice){
            case NBU :
                BigDecimal[][] nbuCurrency = Utils.getNBUcurrency();
                for (int i = 0; i < currency.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(cur[i] + "nbu" + buySale[j], nbuCurrency[i][j]);
                        System.out.println(cur[i] + "nbu" + buySale[j]  + " = " + nbuCurrency[i][j]);
                    }
                }
                break;
            case PB:
                BigDecimal[][] pbCurrency = Utils.getPBcurrency();
                for (int i = 0; i < currency.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(cur[i] + "pb" + buySale[j], pbCurrency[i][j]);
                        System.out.println(cur[i] + "pb" + buySale[j]  + " = " + pbCurrency[i][j]);
                    }
                }
                break;
            case Monobank:
                BigDecimal[][] monoCurrency = Utils.getMonoCurrency();
                for (int i = 0; i < currency.size(); i++) {
                    for (int j = 0; j < buy_sale.size(); j++) {
                        currency.put(cur[i] + "mono" + buySale[j], monoCurrency[i][j]);
                        System.out.println(cur[i] + "mono" + buySale[j]  + " = " + monoCurrency[i][j]);
                    }
                }
                break;
        }
        return currency;
    }
}
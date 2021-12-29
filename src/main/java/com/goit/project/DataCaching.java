package com.goit.project;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import com.goit.project.response.*;

public class DataCaching implements Runnable {

    private static volatile DataCaching instance;
    private static final CurrencyStorage currencyStorage = new CurrencyStorage();
    private DataCaching(){}

    public static DataCaching getInstance(){
        synchronized(DataCaching.class) {
            if (instance == null) {
                instance = new DataCaching();
            }
        }
        return instance;
    }


    private static class CurrencyStorage{

        HashMap<String, Currency> currenciesNBU = new HashMap<>();
        HashMap<String, Currency> currenciesPB = new HashMap<>();
        HashMap<String, Currency> currenciesMono = new HashMap<>();

    }

    private void parseCurrencyResponse(HashMap<String, BigDecimal> curMap) throws Exception {

        for (Map.Entry<String, BigDecimal> entry : curMap.entrySet()) {
                String key = entry.getKey();
                BigDecimal value = entry.getValue();
                Currency currency;
                String abbr = key.substring(0,3);
                if (!(abbr.equals("EUR")||abbr.equals("RUB") || abbr.equals("USD"))){
                    throw new Exception("Invalid currency name in currency rate '" + key + "'");
                }
                String oper = key.substring(key.length()-3);
                if (!(oper.equals("Buy")||(key.substring(key.length()-4)).equals("Sale"))){
                    throw new Exception("Invalid operation name in currency rate '" + key + "'");
                }
                String bank = key.substring(3,5);

                switch (bank){
                    case ("nb"):
                        if (currencyStorage.currenciesNBU.containsKey(abbr)) {
                            currency = currencyStorage.currenciesNBU.get(abbr);
                        } else
                            currency = new Currency("NBU", abbr);
                        currencyStorage.currenciesNBU.put(abbr, currency);
                        break;
                    case ("mo"):
                        if (currencyStorage.currenciesMono.containsKey(abbr)) {
                            currency = currencyStorage.currenciesMono.get(abbr);
                        } else
                            currency = new Currency("Mono", abbr);
                        currencyStorage.currenciesMono.put(abbr, currency);
                        break;
                    case ("pb"):
                        if (currencyStorage.currenciesPB.containsKey(abbr)) {
                            currency = currencyStorage.currenciesPB.get(abbr);
                        } else
                            currency = new Currency("PB", abbr);
                        currencyStorage.currenciesPB.put(abbr, currency);
                        break;
                    default:
                        throw new Exception("Invalid bank name in currency rate '" + key + "'");
                }

                if (oper.equals("Buy")){
                    currency.setRateBuy(value);
                } else {
                    currency.setRateSell(value);
                }

        }

    }

    // Запрос всех валют для одного банка
    // key = currency Name, value = Currency
    public HashMap<String, Currency> getCurrenciesByBank(String bank) throws Exception {
        switch (bank){
            case ("NBU"):
                return currencyStorage.currenciesNBU;
            case ("Mono"):
                return currencyStorage.currenciesMono;
            case ("PB"):
                return currencyStorage.currenciesPB;
            default:
                throw new Exception("Invalid bank name '" + bank + "', must be one of NBU, PB, Mono.");
        }

    }

    // Запрос конкретной валюты для конкретного банка
    public Currency getCurrencyByBank(String abbr, String bank) throws Exception {
        if (!(abbr.equals("EUR")||abbr.equals("RUB") || abbr.equals("USD"))){
            throw new Exception("Invalid currency name '" + abbr + "', must be one of EUR, RUB, PB.");
        }
        switch (bank){
            case ("NBU"):
                return currencyStorage.currenciesNBU.get(abbr);
            case ("Mono"):
                return currencyStorage.currenciesMono.get(abbr);
            case ("PB"):
                return currencyStorage.currenciesPB.get(abbr);
            default:
                throw new Exception("Invalid bank name '" + bank + "', must be one of NBU, PB, Mono.");
        }

    }

    // Запрос конкретной валюты для всех банков
    // key = bankName, value = Currency
    public HashMap<String, Currency> getCurrencyForAllBank(String abbr) throws Exception {
        if (!(abbr.equals("EUR")||abbr.equals("RUB") || abbr.equals("USD"))){
            throw new Exception("Invalid currency name '" + abbr + "', must be one of EUR, RUB, PB.");
        }
        HashMap<String, Currency> currencies = new HashMap<>();
        Currency cur = currencyStorage.currenciesNBU.get(abbr);
        currencies.put(cur.bankName, cur);
        cur = currencyStorage.currenciesMono.get(abbr);
        currencies.put(cur.bankName, cur);
        cur = currencyStorage.currenciesPB.get(abbr);
        currencies.put(cur.bankName, cur);
        return currencies;
    }

    public Currency getEURNBU(){
        return (currencyStorage.currenciesNBU).get("EUR");
    }
    public Currency getUSDNBU(){
        return currencyStorage.currenciesNBU.get("USD");
    }
    public Currency getRUBNBU(){
        return currencyStorage.currenciesNBU.get("RUB");
    }
    public Currency getEURPB(){
        return currencyStorage.currenciesPB.get("EUR");
    }
    public Currency getUSDPB(){
        return currencyStorage.currenciesPB.get("USD");
    }
    public Currency getRUBPB(){
        return currencyStorage.currenciesNBU.get("RUB");
    }
    public Currency getEURMono(){
        return currencyStorage.currenciesNBU.get("EUR");
    }
    public Currency getUSDMono(){
        return currencyStorage.currenciesNBU.get("USD");
    }
    public Currency getRUBMono(){
        return currencyStorage.currenciesNBU.get("RUB");
    }


    @Override
    public synchronized void run() {
        {
            do {
                try {
                    parseCurrencyResponse((new BankResponse(ChoiceBank.PB)).getCurrency());
                    parseCurrencyResponse((new BankResponse(ChoiceBank.NBU)).getCurrency());
                    parseCurrencyResponse((new BankResponse(ChoiceBank.Monobank)).getCurrency());
                    Thread.sleep(5 * 60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}

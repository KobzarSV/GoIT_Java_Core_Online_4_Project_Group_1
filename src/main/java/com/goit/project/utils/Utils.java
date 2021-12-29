package com.goit.project.utils;

import banks.Bank;
import banks.Monobank;
import banks.NBU;
import banks.PB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import response.ChoiceBank;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> buy_sale = List.of(new String[]{"Buy", "Sale"});
    private static final List<String> curr = List.of(new String[]{"USD", "EUR", "RUB"});
    private static final List<Integer>  listOfCodes = List.of(new Integer[]{840, 978, 643});

    public static HashMap<String, BigDecimal> getCurrencies(ChoiceBank choice) throws IOException, InterruptedException {
        String url = "";
        Type type = null;
        String name = "";
        if(ChoiceBank.NBU == choice) {
            url = NBU.URL_NBU;
            type = new TypeToken<List<NBU>>() {}.getType();
            name = "nbu";
        }else if(ChoiceBank.PB == choice){
            url = PB.URL_PB;
            type = new TypeToken<List<PB>>() {}.getType();
            name = "pb";
        }else if(ChoiceBank.Monobank == choice){
            url = Monobank.URL_MONO;
            type = new TypeToken<List<Monobank>>() {}.getType();
            name = "mono";
        }
        URI uri = URI.create(url);
        HttpResponse<String> response = getHttpResponse(uri, CLIENT);
        List<Bank> currencies = GSON.fromJson(response.body(), type);
        HashMap<String, BigDecimal> currency = getCurrenciesOfBankInHashMap(name, currencies);
        return currency;
    }

    private static HashMap<String, BigDecimal> getCurrenciesOfBankInHashMap(String nameOfBank, List<Bank> currencies) {
        HashMap<String, BigDecimal> currency = new HashMap<String, BigDecimal>();
        for (int i = 0; i < curr.size(); i++) {
            List<BigDecimal> temp1 = getCurrenciesOfBank(listOfCodes.get(i), currencies);
            for (int j = 0; j < buy_sale.size(); j++) {
                currency.put(curr.get(i) + nameOfBank + buy_sale.get(j),
                        temp1.get(j));
            }
        }
        return currency;
    }

    private static List <BigDecimal> getCurrenciesOfBank(Integer codeOfCurrency, List<Bank> currencies) {
        List <BigDecimal> res = new ArrayList<>();
        res.add(currencies.stream()
                .filter(bank -> (bank.getCode() == (codeOfCurrency)))
                .map(Bank::getBuy)
                .collect(Collectors.toList()).get(0));
        res.add(currencies.stream()
                .filter(bank -> (bank.getCode() == (codeOfCurrency)))
                .map(Bank::getSale)
                .collect(Collectors.toList()).get(0));
        return res;
    }

    private static HttpResponse<String> getHttpResponse(URI uri, HttpClient client)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

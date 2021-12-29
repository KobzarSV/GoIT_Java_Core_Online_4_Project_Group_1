package utils;

import banks.Bank;
import banks.Monobank;
import banks.NBU;
import banks.PB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import task.ChoiceBank;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String URL_NBU = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final String URL_PB = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String URL_MONO = "https://api.monobank.ua/bank/currency";
    private static final String  [] arrayOfCodesNBU = {"USD", "EUR", "RUB"};
    private static final String  [] arrayOfCodesPB = {"USD", "EUR", "RUR"};
    private static final int  [] arrayOfCodesMono = {840, 978, 643};


    public static BigDecimal[][] getNBUcurrency() throws IOException, InterruptedException {
        URI uri = URI.create(URL_NBU);
        HttpResponse<String> response = getStringHttpResponse(uri, CLIENT);
        List<NBU> currencies = GSON.fromJson(response.body(), new TypeToken<List<NBU>>() {
        }.getType());
        BigDecimal[][] result_ = new BigDecimal[3][2];
        //BigDecimal result = getResult(codeOfCurrency, currencies);
        for (int i = 0; i < arrayOfCodesNBU.length; i++) {
            for (int j = 0; j < 2; j++) {
                result_[i][j] = getResultNBU(arrayOfCodesNBU[i], currencies);
            }
        }
        return result_;
    }



    public static BigDecimal[][] getPBcurrency() throws IOException, InterruptedException {
        URI uri = URI.create(URL_PB);
        HttpResponse<String> response = getStringHttpResponse(uri, CLIENT);
        List<PB> currencies = GSON.fromJson(response.body(), new TypeToken<List<PB>>() {
        }.getType());
        BigDecimal[][] result_ = new BigDecimal[3][2];
        for (int i = 0; i < arrayOfCodesPB.length; i++) {
            BigDecimal temp[] = getResultPB(arrayOfCodesPB[i], currencies);
            for (int j = 0; j < 2; j++) {
                result_[i][j] = temp[j];
            }
        }
        return result_;
    }

    public static BigDecimal[][] getMonoCurrency() throws IOException, InterruptedException {
        URI uri = URI.create(URL_MONO);
        HttpResponse<String> response = getStringHttpResponse(uri, CLIENT);
        List<Monobank> currencies = GSON.fromJson(response.body(), new TypeToken<List<Monobank>>() {
        }.getType());
        BigDecimal[][] result_ = new BigDecimal[3][2];
        for (int i = 0; i < arrayOfCodesMono.length; i++) {
            BigDecimal temp[] = getResultMono(arrayOfCodesMono[i], currencies);
            for (int j = 0; j < 2; j++) {
                result_[i][j] = temp[j];
            }
        }
        return result_;
    }

    public static BigDecimal [][] getCurrency(Bank bank) throws IOException, InterruptedException {
        URI uri = URI.create(bank.getUrl());
        HttpResponse<String> response = getStringHttpResponse(uri, CLIENT);
        Type type = new TypeToken<List<Bank>>() {
        }.getType();
//        List<Bank> currencies = GSON.fromJson(response.body(), new TypeToken<List<Bank>>() {
//        }.getType());
        List<Bank> currencies = GSON.fromJson(response.body(), type);
        BigDecimal[][] result_ = new BigDecimal[3][2];
        for (int i = 0; i < bank.getArrayOfCodes().length; i++) {
            BigDecimal temp[] = getResult(bank.getArrayOfCodes()[i], currencies);
            for (int j = 0; j < 2; j++) {
                result_[i][j] = temp[j];
            }
        }
        return result_;
    }

    private static BigDecimal[] getResultMono(int codeOfCurrency, List<Monobank> currencies) {
        BigDecimal[] result = new BigDecimal[2];

        result[0] = currencies.stream()
                .filter(bank -> (bank.getCurrencyCodeA() == codeOfCurrency))
                .map(Monobank::getRateBuy)
                .collect(Collectors.toList()).get(0);

        result[1] = currencies.stream()
                .filter(bank -> (bank.getCurrencyCodeA() == codeOfCurrency))
                .map(Monobank::getRateSell)
                .collect(Collectors.toList()).get(0);
        return result;

    }

    private static BigDecimal[] getResultPB(String codeOfCurrency, List<PB> currencies) {
        BigDecimal[] result = new BigDecimal[2];

        result[0] = currencies.stream()
                .filter(bank -> bank.getCcy().equals(codeOfCurrency))
                .map(PB::getBuy)
                .collect(Collectors.toList()).get(0);

        result[1] = currencies.stream()
                .filter(bank -> bank.getCcy().equals(codeOfCurrency))
                .map(PB::getSale)
                .collect(Collectors.toList()).get(0);
        return result;
    }

    private static BigDecimal getResultNBU(String codeOfCurrency, List<NBU> currencies) {
        BigDecimal result = currencies.stream()
                .filter(bank -> bank.getCc().equals(codeOfCurrency))
                .map(NBU::getRate)
                .collect(Collectors.toList()).get(0);
        return result;
    }

    private static  BigDecimal[] getResult(int codeOfCurrency, List<Bank> currencies){
        BigDecimal[] result = new BigDecimal[2];

        result[0] = currencies.stream()
                .filter(bank -> bank.getCode() == codeOfCurrency)
                .map(Bank::getBuy)
                .collect(Collectors.toList()).get(0);

        result[1] = currencies.stream()
                .filter(bank -> bank.getCode() == codeOfCurrency)
                .map(Bank::getSale)
                .collect(Collectors.toList()).get(0);
        return result;
    }

    private static HttpResponse<String> getStringHttpResponse(URI uri, HttpClient client)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

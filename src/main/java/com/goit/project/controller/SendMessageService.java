package com.goit.project.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static com.goit.project.controller.Buttons.*;
import static java.util.Arrays.asList;

public class SendMessageService {

    private final ButtonsService buttonsService = new ButtonsService();
    String userID;

    private SendMessage createMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        userID = String.valueOf(update.getMessage().getChatId());
        System.out.println(userID);
        sendMessage.setChatId(userID);
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendMessage startMessage(Update update) {
        String startTextMessage = "Добро пожаловать. Этот бот поможет отслеживать актуальные курсы валют";
        SendMessage sendMessage = createMessage(update, startTextMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(GET_INFO, SETTINGS)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage getInfoDefault(Update update) {
        // вместо DEFAULT_MESSAGE здесь будет метод, который будет отображать курс валют по умолчанию
        //  defaultExchangeRate()
        String defaultInfoMessage = "Курс валют в Приват Банк: USD/UAH\nПокупка: 27.55\nПродажа: 27.95";
        SendMessage sendMessage = createMessage(update, defaultInfoMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(GET_INFO, SETTINGS)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSettings(Update update) {
        String settingsStartMessage = "Меню настроек";
        SendMessage sendMessage = createMessage(update, settingsStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(SIGNS, BANK, CURRENCIES, TIME_OF_NOTIFICATIONS)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSings(Update update) {
        String singsStartMessage = "Выберите кол-во знаков после запятой";
        SendMessage sendMessage = createMessage(update, singsStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + TWO_SINGS, THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTwoSings(Update update) {
        String twoSingsStartMessage = "2";
        SendMessage sendMessage = createMessage(update, twoSingsStartMessage);
        // Здесь будет метод сохранения в настройках 2 знака после запятой
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + TWO_SINGS, THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThreeSings(Update update) {
        String threeSingsStartMessage = "3";
        SendMessage sendMessage = createMessage(update, threeSingsStartMessage);
        // Здесь будет метод сохранения в настройках 3 знака после запятой
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(TWO_SINGS, "✅ " + THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourSings(Update update) {
        String fourSingsStartMessage = "4";
        SendMessage sendMessage = createMessage(update, fourSingsStartMessage);
        // Здесь будет метод сохранения в настройках 4 знака после запятой
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(TWO_SINGS, THREE_SINGS, "✅ " + FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectBank(Update update) {
        String bankStartMessage = "Выберите Банк";
        SendMessage sendMessage = createMessage(update, bankStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(NBU_BANK, "✅ " + PB_BANK, MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectNbuBank(Update update) {
        String nbuBankStartMessage = "НБУ";
        SendMessage sendMessage = createMessage(update, nbuBankStartMessage);
        // Здесь будет метод сохранения в настройках НБУ Банк
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + NBU_BANK, PB_BANK, MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectPrivateBank(Update update) {
        String pbBankStartMessage = "Приват Банк";
        SendMessage sendMessage = createMessage(update, pbBankStartMessage);
        // Здесь будет метод сохранения в настройках Приват Банк
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(NBU_BANK, "✅ " + PB_BANK, MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectMonoBank(Update update) {
        String monoBankStartMessage = "Моно Банк";
        SendMessage sendMessage = createMessage(update, monoBankStartMessage);
        // Здесь будет метод сохранения в настройках MONO Банк
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(NBU_BANK, PB_BANK, "✅ " + MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectCurrency(Update update) {
        String currencyStartMessage = "Выберите валюту";
        SendMessage sendMessage = createMessage(update, currencyStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + USD, EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectUsdCurrency(Update update) {
        String usdStartMessage = "USD";
        SendMessage sendMessage = createMessage(update, usdStartMessage);
        // Здесь будет метод сохранения в настройках USD
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + USD, EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectEurCurrency(Update update) {
        String eurStartMessage = "EUR";
        SendMessage sendMessage = createMessage(update, eurStartMessage);
        // Здесь будет метод сохранения в настройках EUR
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(USD, "✅ " + EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectRubCurrency(Update update) {
        String rubStartMessage = "RUB";
        SendMessage sendMessage = createMessage(update, rubStartMessage);
        // Здесь будет метод сохранения в настройках RUB
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(USD, EUR, "✅ " + RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setNotificationTime(Update update) {
        String numbersStartMessage = "Выберите время уведомлений";
        SendMessage sendMessage = createMessage(update, numbersStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

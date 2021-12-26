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
        // createUser(userID);
        System.out.println(userID);
        sendMessage.setChatId(userID);
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendMessage startMessage(Update update) {
        String startTextMessage = "Добро пожаловать! Этот бот поможет отслеживать актуальные курсы валют \uD83D\uDCB1";
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
        String twoSingsStartMessage = "Выбрано 2 знака после запятой";
        SendMessage sendMessage = createMessage(update, twoSingsStartMessage);
        // Здесь будет метод сохранения в настройках 2 знака после запятой
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + TWO_SINGS, THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThreeSings(Update update) {
        String threeSingsStartMessage = "Выбрано 3 знака после запятой";
        SendMessage sendMessage = createMessage(update, threeSingsStartMessage);
        // Здесь будет метод сохранения в настройках 3 знака после запятой
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(TWO_SINGS, "✅ " + THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourSings(Update update) {
        String fourSingsStartMessage = "Выбрано 4 знака после запятой";
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
        String nbuBankStartMessage = "Выбран НБУ";
        SendMessage sendMessage = createMessage(update, nbuBankStartMessage);
        // Здесь будет метод сохранения в настройках НБУ Банк
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + NBU_BANK, PB_BANK, MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectPrivateBank(Update update) {
        String pbBankStartMessage = "Выбран Приват Банк";
        SendMessage sendMessage = createMessage(update, pbBankStartMessage);
        // Здесь будет метод сохранения в настройках Приват Банк
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(NBU_BANK, "✅ " + PB_BANK, MONO_BANK, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectMonoBank(Update update) {
        String monoBankStartMessage = "Выбран Моно Банк";
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
        String usdStartMessage = "Выбрана валюта USD";
        SendMessage sendMessage = createMessage(update, usdStartMessage);
        // Здесь будет метод сохранения в настройках USD
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + USD, EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectEurCurrency(Update update) {
        String eurStartMessage = "Выбрана валюта EUR";
        SendMessage sendMessage = createMessage(update, eurStartMessage);
        // Здесь будет метод сохранения в настройках EUR
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(USD, "✅ " + EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectRubCurrency(Update update) {
        String rubStartMessage = "Выбрана валюта RUB";
        SendMessage sendMessage = createMessage(update, rubStartMessage);
        // Здесь будет метод сохранения в настройках RUB
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(USD, EUR, "✅ " + RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setNotificationTime(Update update) {
        String setNotificationTimeStartMessage = "Выберите время уведомлений";
        SendMessage sendMessage = createMessage(update, setNotificationTimeStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setNineNotificationTime(Update update) {
        String setNineStartMessage = "Время уведомлений установлено на 9:00";
        SendMessage sendMessage = createMessage(update, setNineStartMessage);
        // метод сохранения в настройках 9 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList("✅ " + NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTenNotificationTime(Update update) {
        String setTenStartMessage = "Время уведомлений установлено на 10:00";
        SendMessage sendMessage = createMessage(update, setTenStartMessage);
        // метод сохранения в настройках 10 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, "✅ " + TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setElevenNotificationTime(Update update) {
        String setElevenStartMessage = "Время уведомлений установлено на 11:00";
        SendMessage sendMessage = createMessage(update, setElevenStartMessage);
        // метод сохранения в настройках 11 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, "✅ " + ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTwelveNotificationTime(Update update) {
        String setTwelveStartMessage = "Время уведомлений установлено на 12:00";
        SendMessage sendMessage = createMessage(update, setTwelveStartMessage);
        // метод сохранения в настройках 12 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList("✅ " + TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThirteenNotificationTime(Update update) {
        String setThirteenStartMessage = "Время уведомлений установлено на 13:00";
        SendMessage sendMessage = createMessage(update, setThirteenStartMessage);
        // метод сохранения в настройках 13 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, "✅ " + THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourteenNotificationTime(Update update) {
        String setFourteenStartMessage = "Время уведомлений установлено на 14:00";
        SendMessage sendMessage = createMessage(update, setFourteenStartMessage);
        // метод сохранения в настройках 14 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, "✅ " + FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFifteenNotificationTime(Update update) {
        String setFifteenStartMessage = "Время уведомлений установлено на 15:00";
        SendMessage sendMessage = createMessage(update, setFifteenStartMessage);
        // метод сохранения в настройках 15 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList("✅ " + FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSixteenNotificationTime(Update update) {
        String setSixteenStartMessage = "Время уведомлений установлено на 16:00";
        SendMessage sendMessage = createMessage(update, setSixteenStartMessage);
        // метод сохранения в настройках 16 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, "✅ " + SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSeventeenNotificationTime(Update update) {
        String setSeventeenStartMessage = "Время уведомлений установлено на 17:00";
        SendMessage sendMessage = createMessage(update, setSeventeenStartMessage);
        // метод сохранения в настройках 17 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, "✅ " + SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setEighteenNotificationTime(Update update) {
        String setEightinStartMessage = "Время уведомлений установлено на 18:00";
        SendMessage sendMessage = createMessage(update, setEightinStartMessage);
        // метод сохранения в настройках 18 часов для уведомлений
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList("✅ " + EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTurnOffNotificationTime(Update update) {
        String setTurnOffStartMessage = "Уведомления отключены";
        SendMessage sendMessage = createMessage(update, setTurnOffStartMessage);
        // метод сохранения в настройках Turn Off Notification Time
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, "✅ " + TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

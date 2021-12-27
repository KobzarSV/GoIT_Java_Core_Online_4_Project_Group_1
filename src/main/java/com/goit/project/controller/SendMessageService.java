package com.goit.project.controller;

import com.goit.project.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Objects;

import static com.goit.project.controller.Buttons.*;
import static java.util.Arrays.asList;

public class SendMessageService {

    private final ButtonsService buttonsService = new ButtonsService();
    int userID;
    UserService userService = UserService.getInstance();

    private SendMessage createMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendMessage startMessage(Update update) {
        userID = Math.toIntExact(update.getMessage().getChatId());
        userService.createUser(userID);
        System.out.println(userID);
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
        String defaultInfoMessage = null;
        try {
            defaultInfoMessage = userService.getInfo(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        userService.setRounding(userID, 2);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + TWO_SINGS, THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThreeSings(Update update) {
        String threeSingsStartMessage = "Выбрано 3 знака после запятой";
        SendMessage sendMessage = createMessage(update, threeSingsStartMessage);
        userService.setRounding(userID, 3);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(TWO_SINGS, "✅ " + THREE_SINGS, FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourSings(Update update) {
        String fourSingsStartMessage = "Выбрано 4 знака после запятой";
        SendMessage sendMessage = createMessage(update, fourSingsStartMessage);
        userService.setRounding(userID, 4);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(TWO_SINGS, THREE_SINGS, "✅ " + FOUR_SINGS, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectBank(Update update) {
        String bankStartMessage = "Выберите Банк";
        SendMessage sendMessage = createMessage(update, bankStartMessage);
        String bank = userService.getBank(userID);
        ReplyKeyboardMarkup keyboardMarkup;
        if (Objects.equals(bank, "NBU")) {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                            asList("✅ " + "NBU", "PB", "Mono", BACK)));
        } else if (Objects.equals(bank, "PB")) {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                            asList("NBU", "✅ " + "PB", "Mono", BACK)));
        } else if (Objects.equals(bank, "Mono")) {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                            asList("NBU", "PB", "✅ " + "Mono", BACK)));
        } else {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                    asList("✅ " + "NBU", "PB", "Mono", BACK)));
        }
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectNbuBank(Update update) {
        String nbuBankStartMessage = "Выбран НБУ";
        SendMessage sendMessage = createMessage(update, nbuBankStartMessage);
        userService.setBank(userID, NBU_BANK);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + "NBU", "PB" , "Mono", BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectPrivateBank(Update update) {
        String pbBankStartMessage = "Выбран Приват Банк";
        SendMessage sendMessage = createMessage(update, pbBankStartMessage);
        userService.setBank(userID, PB_BANK);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("NBU", "✅ " + "PB", "Mono", BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectMonoBank(Update update) {
        String monoBankStartMessage = "Выбран Моно Банк";
        SendMessage sendMessage = createMessage(update, monoBankStartMessage);
        userService.setBank(userID, MONO_BANK);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("NBU", "PB", "✅ " + "Mono", BACK)));
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
        userService.setUsd(userID, !userService.getUsd(userID));
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + USD, EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectEurCurrency(Update update) {
        String eurStartMessage = "Выбрана валюта EUR";
        SendMessage sendMessage = createMessage(update, eurStartMessage);
        userService.setEur(userID, !userService.getEur(userID));
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(USD, "✅ " + EUR, RUB, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectRubCurrency(Update update) {
        String rubStartMessage = "Выбрана валюта RUB";
        SendMessage sendMessage = createMessage(update, rubStartMessage);
        userService.setRub(userID, !userService.getRub(userID));
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
        userService.setSchedulerTime(userID, 9);

        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList("✅ " + NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTenNotificationTime(Update update) {
        String setTenStartMessage = "Время уведомлений установлено на 10:00";
        SendMessage sendMessage = createMessage(update, setTenStartMessage);
        userService.setSchedulerTime(userID, 10);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, "✅ " + TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setElevenNotificationTime(Update update) {
        String setElevenStartMessage = "Время уведомлений установлено на 11:00";
        SendMessage sendMessage = createMessage(update, setElevenStartMessage);
        userService.setSchedulerTime(userID, 11);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, "✅ " + ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTwelveNotificationTime(Update update) {
        String setTwelveStartMessage = "Время уведомлений установлено на 12:00";
        SendMessage sendMessage = createMessage(update, setTwelveStartMessage);
        userService.setSchedulerTime(userID, 12);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList("✅ " + TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThirteenNotificationTime(Update update) {
        String setThirteenStartMessage = "Время уведомлений установлено на 13:00";
        SendMessage sendMessage = createMessage(update, setThirteenStartMessage);
        userService.setSchedulerTime(userID, 13);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, "✅ " + THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourteenNotificationTime(Update update) {
        String setFourteenStartMessage = "Время уведомлений установлено на 14:00";
        SendMessage sendMessage = createMessage(update, setFourteenStartMessage);
        userService.setSchedulerTime(userID, 14);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, "✅ " + FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFifteenNotificationTime(Update update) {
        String setFifteenStartMessage = "Время уведомлений установлено на 15:00";
        SendMessage sendMessage = createMessage(update, setFifteenStartMessage);
        userService.setSchedulerTime(userID, 15);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList("✅ " + FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSixteenNotificationTime(Update update) {
        String setSixteenStartMessage = "Время уведомлений установлено на 16:00";
        SendMessage sendMessage = createMessage(update, setSixteenStartMessage);
        userService.setSchedulerTime(userID, 16);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, "✅ " + SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSeventeenNotificationTime(Update update) {
        String setSeventeenStartMessage = "Время уведомлений установлено на 17:00";
        SendMessage sendMessage = createMessage(update, setSeventeenStartMessage);
        userService.setSchedulerTime(userID, 17);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, "✅ " + SEVENTEEN)), (asList(EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setEighteenNotificationTime(Update update) {
        String setEightinStartMessage = "Время уведомлений установлено на 18:00";
        SendMessage sendMessage = createMessage(update, setEightinStartMessage);
        userService.setSchedulerTime(userID, 18);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList("✅ " + EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTurnOffNotificationTime(Update update) {
        String setTurnOffStartMessage = "Уведомления отключены";
        SendMessage sendMessage = createMessage(update, setTurnOffStartMessage);
        userService.setScheduler(userID, false);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN)), (asList(TWELVE, THIRTEEN, FOURTEEN)),
                        (asList(FIFTEEN, SIXTEEN, SEVENTEEN)), (asList(EIGHTEEN, "✅ " + TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

package com.goit.project.controller;

import com.goit.project.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.goit.project.controller.Buttons.*;
import static java.util.Arrays.asList;

public class SendMessageService {

    private final ButtonsService buttonsService = new ButtonsService();
    UserService userService = UserService.getInstance();
    boolean beInAdvancedSettings = false;

    private SendMessage createMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendMessage startMessage(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        userService.createUser(userID);
        System.out.println(userID);
        String startTextMessage = "Добро пожаловать! Этот бот поможет отслеживать актуальные курсы валют \uD83D\uDCB1";
        SendMessage sendMessage = createMessage(update, startTextMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(GET_INFO, SETTINGS)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage getInfo(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
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

    public SendMessage getInfo(Update update, int userID) {
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

    public SendMessage startMenu(Update update) {
        String startMenu = "Меню";
        SendMessage sendMessage = createMessage(update, startMenu);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(GET_INFO, SETTINGS)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSettings(Update update) {
        String settingsStartMessage = "Меню настроек";
        SendMessage sendMessage = createMessage(update, settingsStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows(
                        asList(CURRENCIES, BANK), asList(SIGNS, TIME_OF_NOTIFICATIONS), asList(BACK));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSings(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String singsStartMessage = "Выберите кол-во знаков после запятой";
        SendMessage sendMessage = createMessage(update, singsStartMessage);
        int rounding = userService.getRounding(userID);
        ReplyKeyboardMarkup keyboardMarkup;
        beInAdvancedSettings = true;
        if (rounding == 2) {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                    asList("✅ " + TWO_SINGS, THREE_SINGS, FOUR_SINGS, BACK)));
        } else if (rounding == 3) {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                    asList(TWO_SINGS, "✅ " + THREE_SINGS, FOUR_SINGS, BACK)));
        } else {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                    asList(TWO_SINGS, THREE_SINGS, "✅ " + FOUR_SINGS, BACK)));
        }
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTwoSings(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        } else {
            keyboardMarkup = buttonsService.setButtons(buttonsService.createButtons(
                    asList("NBU", "PB", "✅ " + "Mono", BACK)));
        }
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectNbuBank(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String nbuBankStartMessage = "Выбран НБУ";
        SendMessage sendMessage = createMessage(update, nbuBankStartMessage);
        userService.setBank(userID, NBU_BANK);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList("✅ " + "NBU", "PB", "Mono", BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage selectPrivateBank(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
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
        List<String> buttonsList = createButtonsList(update);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(buttonsList));
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage changeUsdCurrency(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String usdStartMessage = "Изменена валюта USD";
        SendMessage sendMessage = createMessage(update, usdStartMessage);
        userService.setUsd(userID, !userService.getUsd(userID));
        List<String> buttonsList = createButtonsList(update);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(buttonsList));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage changeEurCurrency(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String eurStartMessage = "Изменена валюта EUR";
        SendMessage sendMessage = createMessage(update, eurStartMessage);
        userService.setEur(userID, !userService.getEur(userID));
        List<String> buttonsList = createButtonsList(update);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(buttonsList));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage changeRubCurrency(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String rubStartMessage = "Изменена валюта RUB";
        SendMessage sendMessage = createMessage(update, rubStartMessage);
        userService.setRub(userID, !userService.getRub(userID));
        List<String> buttonsList = createButtonsList(update);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(buttonsList));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private List<String> createButtonsList(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        boolean usd = userService.getUsd(userID);
        boolean eur = userService.getEur(userID);
        boolean rub = userService.getRub(userID);
        List<String> buttonsList = new ArrayList<>();
        if (usd) {
            buttonsList.add("✅ " + USD);
        } else {
            buttonsList.add(USD);
        }
        if (eur) {
            buttonsList.add("✅ " + EUR);
        } else {
            buttonsList.add(EUR);
        }
        if (rub) {
            buttonsList.add("✅ " + RUB);
        } else {
            buttonsList.add(RUB);
        }
        buttonsList.add(BACK);
        return buttonsList;
    }

    public SendMessage setNotificationTime(Update update) {
        String setNotificationTimeStartMessage = "Выберите время уведомлений";
        SendMessage sendMessage = createMessage(update, setNotificationTimeStartMessage);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setNineNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setNineStartMessage = "Время уведомлений установлено на 9:00";
        SendMessage sendMessage = createMessage(update, setNineStartMessage);
        userService.setSchedulerTime(userID, 9);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList("✅ " + NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setTenStartMessage = "Время уведомлений установлено на 10:00";
        SendMessage sendMessage = createMessage(update, setTenStartMessage);
        userService.setSchedulerTime(userID, 10);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, "✅ " + TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setElevenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setElevenStartMessage = "Время уведомлений установлено на 11:00";
        SendMessage sendMessage = createMessage(update, setElevenStartMessage);
        userService.setSchedulerTime(userID, 11);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, "✅ " + ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setTwelveNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setTwelveStartMessage = "Время уведомлений установлено на 12:00";
        SendMessage sendMessage = createMessage(update, setTwelveStartMessage);
        userService.setSchedulerTime(userID, 12);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, "✅ " + TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setThirteenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setThirteenStartMessage = "Время уведомлений установлено на 13:00";
        SendMessage sendMessage = createMessage(update, setThirteenStartMessage);
        userService.setSchedulerTime(userID, 13);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList("✅ " + THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFourteenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setFourteenStartMessage = "Время уведомлений установлено на 14:00";
        SendMessage sendMessage = createMessage(update, setFourteenStartMessage);
        userService.setSchedulerTime(userID, 14);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, "✅ " + FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setFifteenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setFifteenStartMessage = "Время уведомлений установлено на 15:00";
        SendMessage sendMessage = createMessage(update, setFifteenStartMessage);
        userService.setSchedulerTime(userID, 15);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, "✅ " + FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSixteenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setSixteenStartMessage = "Время уведомлений установлено на 16:00";
        SendMessage sendMessage = createMessage(update, setSixteenStartMessage);
        userService.setSchedulerTime(userID, 16);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, "✅ " + SIXTEEN)),
                        (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setSeventeenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setSeventeenStartMessage = "Время уведомлений установлено на 17:00";
        SendMessage sendMessage = createMessage(update, setSeventeenStartMessage);
        userService.setSchedulerTime(userID, 17);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList("✅ " + SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setEighteenNotificationTime(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setEightinStartMessage = "Время уведомлений установлено на 18:00";
        SendMessage sendMessage = createMessage(update, setEightinStartMessage);
        userService.setSchedulerTime(userID, 18);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                        (asList(SEVENTEEN, "✅ " + EIGHTEEN, TURN_OFF, BACK)));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage switchScheduler(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        boolean scheduler = userService.getScheduler(userID);
        String startMessage;
        if (scheduler) {
            startMessage = "Уведомления включены";
        } else {
            startMessage = "Уведомления отключены";
        }
        SendMessage sendMessage = createMessage(update, startMessage);
        userService.setScheduler(userID, !scheduler);
        ReplyKeyboardMarkup keyboardMarkup;
        if (scheduler) {
            keyboardMarkup = buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                    (asList(SEVENTEEN, EIGHTEEN, "✅ " + TURN_OFF, BACK)));
        } else {
            keyboardMarkup = buttonsService.setButtonsRows((asList(NINE, TEN, ELEVEN, TWELVE)), (asList(THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN)),
                    (asList(SEVENTEEN, EIGHTEEN, TURN_OFF, BACK)));
        }
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

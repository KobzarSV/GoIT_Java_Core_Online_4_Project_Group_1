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

    private static SendMessageService sendMessageService;

    public static synchronized SendMessageService getSendMessageService() {
        if (sendMessageService == null) {
            sendMessageService = new SendMessageService();
        }
        return sendMessageService;
    }

    public SendMessageService() {
    }

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

    private SendMessage createMessageForScheduler(Integer userId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(userId));
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendMessage getInfoForScheduler(int userID) {
        String defaultInfoMessage = null;
        try {
            defaultInfoMessage = userService.getInfo(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SendMessage sendMessage = createMessageForScheduler(userID, defaultInfoMessage);
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String currencyStartMessage = "Выберите валюту";
        SendMessage sendMessage = createMessage(update, currencyStartMessage);
        List<String> buttonsList = createButtonsListCurrency(userID);
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
        List<String> buttonsList = createButtonsListCurrency(userID);
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
        List<String> buttonsList = createButtonsListCurrency(userID);
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
        List<String> buttonsList = createButtonsListCurrency(userID);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(buttonsList));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private List<String> createButtonsListCurrency(int userID) {
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
        int userID = Math.toIntExact(update.getMessage().getChatId());
        String setNotificationTimeStartMessage = "Выберите время уведомлений";
        SendMessage sendMessage = createMessage(update, setNotificationTimeStartMessage);
        List<List<String>> buttonsList = createButtonsListNotificationTimes(userID);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((buttonsList.get(0)), (buttonsList.get(1)),
                        (buttonsList.get(2)));
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage setNotificationTimes(Update update, int time) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        boolean scheduler = userService.getScheduler(userID);
        String setTimeStartMessage;
        if (!scheduler) {
            setTimeStartMessage = "Режим напоминания не включен";
        } else {
            userService.setSchedulerTime(userID, time);
            setTimeStartMessage = "Время уведомлений установлено на " + time + ":00";
        }
        SendMessage sendMessage = createMessage(update, setTimeStartMessage);
        List<List<String>> buttonsList = createButtonsListNotificationTimes(userID);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((buttonsList.get(0)), (buttonsList.get(1)),
                        (buttonsList.get(2)));
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private List<List<String>> createButtonsListNotificationTimes(int userID) {
        boolean scheduler = userService.getScheduler(userID);
        int schedulerTime = userService.getSchedulerTime(userID);
        if (!scheduler) {
            schedulerTime = -1;
        }
        List<List<String>> ButtonsListNotificationTimes = new ArrayList<>();
        List<String> buttonsFirstRowList = new ArrayList<>();
        List<String> buttonsSecondRowList = new ArrayList<>();
        List<String> buttonsThirdRowList = new ArrayList<>();
        if (schedulerTime == 9) {
            buttonsFirstRowList.add("✅ " + NINE);
        } else {
            buttonsFirstRowList.add(NINE);
        }
        if (schedulerTime == 10) {
            buttonsFirstRowList.add("✅ " + TEN);
        } else {
            buttonsFirstRowList.add(TEN);
        }
        if (schedulerTime == 11) {
            buttonsFirstRowList.add("✅ " + ELEVEN);
        } else {
            buttonsFirstRowList.add(ELEVEN);
        }
        if (schedulerTime == 12) {
            buttonsFirstRowList.add("✅ " + TWELVE);
        } else {
            buttonsFirstRowList.add(TWELVE);
        }
        if (schedulerTime == 13) {
            buttonsSecondRowList.add("✅ " + THIRTEEN);
        } else {
            buttonsSecondRowList.add(THIRTEEN);
        }
        if (schedulerTime == 14) {
            buttonsSecondRowList.add("✅ " + FOURTEEN);
        } else {
            buttonsSecondRowList.add(FOURTEEN);
        }
        if (schedulerTime == 15) {
            buttonsSecondRowList.add("✅ " + FIFTEEN);
        } else {
            buttonsSecondRowList.add(FIFTEEN);
        }
        if (schedulerTime == 16) {
            buttonsSecondRowList.add("✅ " + SIXTEEN);
        } else {
            buttonsSecondRowList.add(SIXTEEN);
        }
        if (schedulerTime == 17) {
            buttonsThirdRowList.add("✅ " + SEVENTEEN);
        } else {
            buttonsThirdRowList.add(SEVENTEEN);
        }
        if (schedulerTime == 18) {
            buttonsThirdRowList.add("✅ " + EIGHTEEN);
        } else {
            buttonsThirdRowList.add(EIGHTEEN);
        }
        if (scheduler) {
            buttonsThirdRowList.add("✅ " + TURN_OFF);
        } else {
            buttonsThirdRowList.add(TURN_OFF);
        }
        buttonsThirdRowList.add(BACK);

        ButtonsListNotificationTimes.add(buttonsFirstRowList);
        ButtonsListNotificationTimes.add(buttonsSecondRowList);
        ButtonsListNotificationTimes.add(buttonsThirdRowList);
        return ButtonsListNotificationTimes;
    }

    public SendMessage switchScheduler(Update update) {
        int userID = Math.toIntExact(update.getMessage().getChatId());
        boolean scheduler = userService.getScheduler(userID);
        String startMessage;
        if (scheduler) {
            startMessage = "Уведомления отключены";
        } else {
            startMessage = "Уведомления включены";
        }
        SendMessage sendMessage = createMessage(update, startMessage);
        userService.setScheduler(userID, !scheduler);
        List<List<String>> buttonsList = createButtonsListNotificationTimes(userID);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtonsRows((buttonsList.get(0)), (buttonsList.get(1)),
                        (buttonsList.get(2)));
        beInAdvancedSettings = true;
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

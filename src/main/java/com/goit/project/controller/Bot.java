package com.goit.project.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.goit.project.controller.Buttons.*;

public class Bot extends TelegramLongPollingBot {

    SendMessageService sendMessageService = new SendMessageService();

    @Override
    public String getBotUsername() {
        return "botName";
    }

    @Override
    public String getBotToken() {
        return "botToken";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START:
                    executeMessage(sendMessageService.startMessage(update));
                    break;
                case GET_INFO:
                    executeMessage(sendMessageService.getInfoDefault(update));
                    break;
                case SETTINGS:
                    executeMessage(sendMessageService.setSettings(update));
                    break;
                case BACK:
                    executeMessage(sendMessageService.getInfoDefault(update));
                    break;
                case SIGNS:
                    executeMessage(sendMessageService.setSings(update));
                    break;
                case TWO_SINGS:
                    executeMessage(sendMessageService.setTwoSings(update));
                    break;
                case THREE_SINGS:
                    executeMessage(sendMessageService.setThreeSings(update));
                    break;
                case FOUR_SINGS:
                    executeMessage(sendMessageService.setFourSings(update));
                    break;
                case BANK:
                    executeMessage(sendMessageService.selectBank(update));
                    break;
                case NBU_BANK:
                    executeMessage(sendMessageService.selectNbuBank(update));
                    break;
                case PB_BANK:
                    executeMessage(sendMessageService.selectPrivateBank(update));
                    break;
                case MONO_BANK:
                    executeMessage(sendMessageService.selectMonoBank(update));
                    break;
                case CURRENCIES:
                    executeMessage(sendMessageService.selectCurrency(update));
                    break;
                case USD:
                    executeMessage(sendMessageService.selectUsdCurrency(update));
                    break;
                case EUR:
                    executeMessage(sendMessageService.selectEurCurrency(update));
                    break;
                case RUB:
                    executeMessage(sendMessageService.selectRubCurrency(update));
                    break;
                case TIME_OF_NOTIFICATIONS:
                    executeMessage(sendMessageService.setNotificationTime(update));
                    break;
                case NINE:
                    executeMessage(sendMessageService.setNineNotificationTime(update));
                    break;
                case TEN:
                    executeMessage(sendMessageService.setTenNotificationTime(update));
                    break;
                case ELEVEN:
                    executeMessage(sendMessageService.setElevenNotificationTime(update));
                    break;
                case TWELVE:
                    executeMessage(sendMessageService.setTwelveNotificationTime(update));
                    break;
                case THIRTEEN:
                    executeMessage(sendMessageService.setThirteenNotificationTime(update));
                    break;
                case FOURTEEN:
                    executeMessage(sendMessageService.setFourteenNotificationTime(update));
                    break;
                case FIFTEEN:
                    executeMessage(sendMessageService.setFifteenNotificationTime(update));
                    break;
                case SIXTEEN:
                    executeMessage(sendMessageService.setSixteenNotificationTime(update));
                    break;
                case SEVENTEEN:
                    executeMessage(sendMessageService.setSeventeenNotificationTime(update));
                    break;
                case EIGHTEEN:
                    executeMessage(sendMessageService.setEighteenNotificationTime(update));
                    break;
                case TURN_OFF:
                    executeMessage(sendMessageService.setTurnOffNotificationTime(update));
                    break;
                default:
                    break;
            }
        }
    }

    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

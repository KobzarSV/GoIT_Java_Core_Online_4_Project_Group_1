package com.goit.project.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.goit.project.controller.Buttons.*;

public class Bot extends TelegramLongPollingBot {

    SendMessageService sendMessageService = new SendMessageService();

    @Override
    public String getBotUsername() {
        return "@KSVtestMyBot";
    }

    @Override
    public String getBotToken() {
        return "5020369693:AAG-6fhATMoHmc5Bs6rx4k0WXLIYpSDjjP4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START:
                    executeMessage(sendMessageService.startMessage(update));
                    break;
                case GET_INFO:
                    executeMessage(sendMessageService.getInfoMessage(update));
                    break;
                case SETTINGS:
                    executeMessage(sendMessageService.settingsMessage(update));
                    break;
                case BACK:
                    executeMessage(sendMessageService.getInfoMessage(update));
                    break;
                case SIGNS:
                    executeMessage(sendMessageService.singsMessage(update));
                    break;
                case TWO_SINGS:
                    executeMessage(sendMessageService.twoSingsMessage(update));
                    break;
                case THREE_SINGS:
                    executeMessage(sendMessageService.threeSingsMessage(update));
                    break;
                case FOUR_SINGS:
                    executeMessage(sendMessageService.fourSingsMessage(update));
                    break;
                case BANK:
                    executeMessage(sendMessageService.bankMessage(update));
                    break;
                case NBU_BANK:
                    executeMessage(sendMessageService.nbuBankMessage(update));
                    break;
                case PB_BANK:
                    executeMessage(sendMessageService.pbBankMessage(update));
                    break;
                case MONO_BANK:
                    executeMessage(sendMessageService.monoBankMessage(update));
                    break;
                case CURRENCIES:
                    executeMessage(sendMessageService.currencyMessage(update));
                    break;
                case USD:
                    executeMessage(sendMessageService.usdMessage(update));
                    break;
                case EUR:
                    executeMessage(sendMessageService.eurMessage(update));
                    break;
                case RUB:
                    executeMessage(sendMessageService.rubMessage(update));
                    break;
            }
        }
    }

    private <T extends BotApiMethod> void executeMessage(T sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

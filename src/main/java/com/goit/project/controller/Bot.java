package com.goit.project.controller;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static com.goit.project.controller.Buttons.*;

public class Bot extends TelegramLongPollingBot {

    private static final String FILE_PATH = "src/main/resources/bot_info.txt";
    File file = new File(FILE_PATH);

    private static String[] getBotInfo(File file) throws IOException {
        String[] botInfo = new String[2];
        if (!file.exists()){
            throw new IOException("File \"" + file.getName() + "\" in path \"" + file.getParent() + "\" not found!");
        }
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file));) {
            botInfo[0] = inputStream.readLine();
            botInfo[1] = inputStream.readLine();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return botInfo;
    }

    SendMessageService sendMessageService = new SendMessageService();

    @SneakyThrows
    @Override
    public String getBotUsername() {
        return getBotInfo(file)[0];
    }

    @SneakyThrows
    @Override
    public String getBotToken() {
        return getBotInfo(file)[1];
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
                    executeMessage(sendMessageService.changeUsdCurrency(update));
                    break;
                case ("✅ " + USD):
                    executeMessage(sendMessageService.changeUsdCurrency(update));
                    break;
                case EUR:
                    executeMessage(sendMessageService.changeEurCurrency(update));
                    break;
                case ("✅ " + EUR):
                    executeMessage(sendMessageService.changeEurCurrency(update));
                    break;
                case RUB:
                    executeMessage(sendMessageService.changeRubCurrency(update));
                    break;
                case ("✅ " + RUB):
                    executeMessage(sendMessageService.changeRubCurrency(update));
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
                    System.out.println("Команда не существует");
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

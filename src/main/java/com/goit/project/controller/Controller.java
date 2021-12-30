package com.goit.project.controller;

import com.goit.project.DataCaching;
import com.goit.project.Scheduler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Controller {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot currencyBot = new Bot();
        telegramBotsApi.registerBot(currencyBot);

        // Scheduler и DataCaching надо запускать отдельными потоками
        Scheduler scheduler = new Scheduler(currencyBot);
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
        DataCaching dataCache = DataCaching.getInstance();
        Thread dcThread = new Thread(dataCache);
        dcThread.start();
    }
}

package com.lomakin;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botApi = new TelegramBotsApi();

        try {
            botApi.registerBot(new TranslaterBot());
        } catch (TelegramApiRequestException e) {
            System.out.println(e.getMessage());
        }
    }
}

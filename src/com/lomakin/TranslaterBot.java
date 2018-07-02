package com.lomakin;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslaterBot extends TelegramLongPollingBot {

    private Logger log = Logger.getLogger("TranslaterBot");

    /**
     * Прием сообщений. Логика бота.
     * @param update - содержит сообщение от пользователя
     */
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){

            if (update.getMessage().getText().equals("/start")){
                String helloMessage = "Привет! Это бот для перевода текста с английского на русский и наоборот. " +
                        "Он сам определит язык, поэтому ты можешь просто написать ему текст, который нужно " +
                        "перевести, например \"привет\" или \"hello\"" +
                        "\nПервод осуществляется сервисом «Яндекс.Переводчик» http://translate.yandex.ru/";

                sendMsg(update.getMessage().getChatId(), helloMessage);
                return;
            }

            String textMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.log(Level.INFO, "User: " + update.getMessage().getContact().getFirstName() + "\n");
            log.log(Level.INFO, "Message received: " + textMessage + "\n");

            YandexTranslate yt = new YandexTranslate();
            String answer = null;
            try {
                answer = yt.translate("en", textMessage);
                if (answer.equals(textMessage)){
                    answer = yt.translate("ru", textMessage);
                }
            } catch (IOException e) {
                sendMsg(chatId, "Что-то пошло не так. Можете написать @lomgrey о случившейся проблеме. ");
            }

            sendMsg(chatId, answer);
        }
    }

    /**
     * Имя бота, указанное при регистрации
     * @return - имя бота
     */
    public String getBotUsername() {
        return "trnsltr_bot";
    }

    /**
     * Возвращает token бота для связи с сервером телеграм
     * @return - token бота
     */
    public String getBotToken() {
        return "518174279:AAHJ9BsfbJ5RM5I3CA4gdJztFQE-w6KqxsY";
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    private synchronized void sendMsg(long chatId, String s) {
        String license = "\n\nПереведено сервисом «Яндекс.Переводчик» http://translate.yandex.ru/";

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }
}

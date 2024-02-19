package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class TrackCommand implements Command {
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public boolean supports(Update update) {
        if (update.message() != null) {
            if (update.message().text() != null) {
                String text = update.message().text();
                return text.substring(0, 6).equals(command()) && text.charAt(6) == ' ';
            }
        }
        return false;
    }

    @Override
    public SendMessage handle(Update update) {

        String messageText = "Введите корректную ссылку";
        String input = update.message().text();
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex != -1) {
            String url = input.substring(spaceIndex + 1).trim();
            if (UrlChecker.isValid(url)) {

                //Проверка на наличие ссылки в списке
                //Добавление ссылки в список
                messageText = "Ссылка успешно добавлена к отслеживанию: " + url;
            }
        }

        return new SendMessage(update.message().chat().id(), messageText);
    }
}

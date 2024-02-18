package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UntrackCommand implements Command {
    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null
            && update.message().text().startsWith(command());
    }

    @Override
    public SendMessage handle(Update update) {

        String messageText = "Введите корректную ссылку";
        String input = update.message().text();
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex != -1) {
            String url = input.substring(spaceIndex + 1);
            if (UrlChecker.isValid(url)) {

                //Проверить, что данная ссылка есть в списке
                //Удаление ссылки из списка
                messageText = "Ссылка успешно удалена из отслеживания: " + url;
            }
        }
        return new SendMessage(update.message().chat().id(), messageText);
    }
}

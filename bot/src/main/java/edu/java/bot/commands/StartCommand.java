package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand implements Command {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

    @Override
    public SendMessage handle(Update update) {

        String userId = update.message().from().id().toString();
        String firstName = update.message().from().firstName();
        String lastName = update.message().from().lastName();
        String username = update.message().from().username();

        String messageText = String.format("Пользователь %s %s %s (ID: %s) успешно зарегистрирован.", username,
            firstName, lastName, userId);

        return new SendMessage(update.message().chat().id(), messageText);
    }
}



package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        if (update.message() != null) {
            String text = update.message().text();
            return text != null && (text.equals(command()) || text.startsWith(command() + ' '));
        } else {
            return false;
        }
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}

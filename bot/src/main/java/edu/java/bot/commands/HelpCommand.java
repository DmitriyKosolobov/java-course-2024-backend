package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Вывести окно с командами";
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

    @Override
    public SendMessage handle(Update update) {

        StringBuilder messageText = new StringBuilder("Список доступных команд:\n");
        List<Command> commands = CommandRegistry.getRegisteredCommands();

        for (Command com : commands) {
            messageText.append(com.command())
                .append(" - ")
                .append(com.description())
                .append("\n");
        }
//        String messageText = "Список доступных команд:\n"
//            + "/start - Зарегистрировать пользователя\n"
//            + "/help - Вывести окно с командами\n"
//            + "/track - Начать отслеживание ссылки\n"
//            + "/untrack - Прекратить отслеживание ссылки\n"
//            + "/list - Показать список отслеживаемых ссылок";

        return new SendMessage(update.message().chat().id(), messageText.toString());
    }
}

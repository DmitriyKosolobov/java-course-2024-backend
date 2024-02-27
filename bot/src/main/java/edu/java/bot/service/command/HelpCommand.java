package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {

    private final List<Command> commands;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        this.commands.addFirst(this);
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        String messageText = "Список доступных команд:\n"
            + commands.stream()
                .map(com -> com.command() + " - " + com.description())
                .collect(Collectors.joining("\n"));
        return new SendMessage(update.message().chat().id(), messageText);
    }
}

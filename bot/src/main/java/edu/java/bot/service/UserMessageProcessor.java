package edu.java.bot.service;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.command.Command;
import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    BotCommand[] commandsForMenu();

    SendMessage process(Update update);
}

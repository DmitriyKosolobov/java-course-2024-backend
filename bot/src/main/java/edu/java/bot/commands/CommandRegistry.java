package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("HideUtilityClassConstructor")
public class CommandRegistry {
    public static List<Command> getRegisteredCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new StartCommand());
        commands.add(new HelpCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        return commands;
    }

    public static BotCommand[] getCommandsForMenu() {
        List<Command> commandsList = getRegisteredCommands();
        int size = commandsList.size();
        BotCommand[] commands = new BotCommand[size];
        for (int i = 0; i < size; i++) {
            commands[i] = commandsList.get(i).toApiCommand();
        }
        return commands;
    }
}

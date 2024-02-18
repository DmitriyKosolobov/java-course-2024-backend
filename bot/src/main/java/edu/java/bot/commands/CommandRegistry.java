package edu.java.bot.commands;

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
}

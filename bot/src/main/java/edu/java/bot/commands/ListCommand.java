package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;

public class ListCommand implements Command {
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

    @Override
    public SendMessage handle(Update update) {

        ArrayList<String> linkList = new ArrayList<>();

        StringBuilder messageText = new StringBuilder();
        if (linkList.isEmpty()) {
            messageText.append("Список отслеживаемых ссылок пуст!");
        } else {
            for (int i = 0; i < linkList.size(); i++) {
                messageText.append(i).append(": ").append(linkList.get(i)).append("\n");
            }
        }

        return new SendMessage(update.message().chat().id(), messageText.toString());
    }
}

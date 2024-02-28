package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
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

package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.controller.dto.ErrorResponse;
import edu.java.bot.controller.dto.LinkResponse;
import edu.java.bot.controller.dto.RemoveLinkRequest;
import edu.java.bot.util.UrlChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UntrackCommand implements Command {
    @Autowired
    ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {

        Long tgChatId = update.message().chat().id();

        String messageText = "Введите корректную ссылку";
        String input = update.message().text();
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex != -1) {
            String url = input.substring(spaceIndex + 1).trim();
            if (UrlChecker.isValid(url)) {
                try {
                    LinkResponse linkResponse = scrapperClient.deleteLink(tgChatId, new RemoveLinkRequest(url));
                    messageText = "Ссылка успешно удалена из отслеживания: " + linkResponse.url();
                } catch (HttpClientErrorException e) {
                    ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
                    if (errorResponse != null) {
                        messageText = errorResponse.description();
                    }
                }
            }
        }
        return new SendMessage(tgChatId, messageText);
    }
}

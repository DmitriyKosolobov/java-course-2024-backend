package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.controller.dto.AddLinkRequest;
import edu.java.bot.controller.dto.ErrorResponse;
import edu.java.bot.controller.dto.LinkResponse;
import edu.java.bot.util.UrlChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class TrackCommand implements Command {
    @Autowired
    ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
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
                    LinkResponse linkResponse = scrapperClient.createLink(tgChatId, new AddLinkRequest(url));
                    messageText = "Ссылка успешно добавлена к отслеживанию: " + linkResponse.url();
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

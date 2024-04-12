package edu.java.scrapper.service.sender;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.controller.dto.UpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class HttpSender implements SenderNotification {

    private final BotClient botClient;

    public HttpSender(BotClient botClient) {
        this.botClient = botClient;
    }

    @Override
    public void send(UpdateRequest updateRequest) {
        botClient.updates(updateRequest);
    }
}

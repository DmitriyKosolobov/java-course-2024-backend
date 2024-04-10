package edu.java.scrapper.client;

import edu.java.scrapper.controller.dto.UpdateRequest;
import org.springframework.retry.support.RetryTemplate;

public class RetryableBotClient implements BotClient {

    private final RetryTemplate retryTemplate;
    private final BotClient botClient;

    public RetryableBotClient(RetryTemplate retryTemplate, BotClient botClient) {
        this.retryTemplate = retryTemplate;
        this.botClient = botClient;
    }

    @Override
    public void updates(UpdateRequest updateRequest) {
        retryTemplate.execute((context -> {
            botClient.updates(updateRequest);
            return null;
        }));
    }
}

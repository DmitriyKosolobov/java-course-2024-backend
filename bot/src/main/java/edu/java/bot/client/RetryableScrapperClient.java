package edu.java.bot.client;

import edu.java.bot.controller.dto.AddLinkRequest;
import edu.java.bot.controller.dto.LinkResponse;
import edu.java.bot.controller.dto.ListLinksResponse;
import edu.java.bot.controller.dto.RemoveLinkRequest;
import org.springframework.retry.support.RetryTemplate;

public class RetryableScrapperClient implements ScrapperClient {

    private final RetryTemplate retryTemplate;
    private final ScrapperClient scrapperClient;

    public RetryableScrapperClient(RetryTemplate retryTemplate, ScrapperClient scrapperClient) {
        this.retryTemplate = retryTemplate;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void createTgChat(Long id) {
        retryTemplate.execute((context -> {
            scrapperClient.createTgChat(id);
            return null;
        }));
    }

    @Override
    public void deleteTgChat(Long id) {
        retryTemplate.execute((context -> {
            scrapperClient.deleteTgChat(id);
            return null;
        }));
    }

    @Override
    public ListLinksResponse getLinks(Long chatId) {
        return retryTemplate.execute((context -> scrapperClient.getLinks(chatId)));
    }

    @Override
    public LinkResponse createLink(Long chatId, AddLinkRequest addLinkRequest) {
        return retryTemplate.execute((context -> scrapperClient.createLink(chatId, addLinkRequest)));
    }

    @Override
    public LinkResponse deleteLink(Long chatId, RemoveLinkRequest removeLinkRequest) {
        return retryTemplate.execute((context -> scrapperClient.deleteLink(chatId, removeLinkRequest)));
    }

}

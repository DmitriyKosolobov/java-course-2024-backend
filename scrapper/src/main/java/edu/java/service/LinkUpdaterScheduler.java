package edu.java.service;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.client.dto.GitHubRepositoryResponse;
import edu.java.client.dto.StackOverflowQuestionItem;
import edu.java.controller.dto.UpdateRequest;
import edu.java.repository.dto.Link;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    private final GitHubClient gitHubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;

    public LinkUpdaterScheduler(
        LinkUpdater linkUpdater, GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient, BotClient botClient
    ) {
        this.linkUpdater = linkUpdater;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("Updating links...");
        Collection<Link> links = linkUpdater.listAllOldCheckedLinks();
        for (Link link : links) {
            checkLink(link);
        }
    }

    private void checkLink(Link link) {
        URI url = URI.create(link.url());
        String host = url.getHost();

        if (host.equals("github.com")) {
            gitHubHandler(link, url);
        } else if (host.equals("stackoverflow.com")) {
            stackOverflowHandler(link, url);
        }
    }

    private void gitHubHandler(Link link, URI url) {
        String[] path = url.getPath().split("/");
        String owner = path[1];
        String repo = path[2];
        GitHubRepositoryResponse res = gitHubClient.fetchRepository(owner, repo);
        if (link.lastCheckTime().isBefore(res.pushedAt())
            || link.lastCheckTime().isBefore(res.updatedAt())) {

            linkUpdater.update(link.id());
            List<Long> tgChatIds = linkUpdater.listAllTgChatIdByLinkId(link.id());
            sendBotUpdates(link, "Обновление в репозитории", tgChatIds);
        }
    }

    private void stackOverflowHandler(Link link, URI url) {
        Long questionId = Long.parseLong(url.getPath().split("/")[2]);
        StackOverflowQuestionItem res = stackOverflowClient.fetchQuestion(questionId).items().getFirst();
        if (link.lastCheckTime().isBefore(res.lastActivityDate())) {

            linkUpdater.update(link.id());
            List<Long> tgChatIds = linkUpdater.listAllTgChatIdByLinkId(link.id());
            sendBotUpdates(link, "Новая информация по вопросу", tgChatIds);
        }
    }

    private void sendBotUpdates(Link link, String description, List<Long> tgChatIds) {
        botClient.updates(new UpdateRequest(
            link.id(),
            link.url(),
            description,
            tgChatIds
        ));
    }
}

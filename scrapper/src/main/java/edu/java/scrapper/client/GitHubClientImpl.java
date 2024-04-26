package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.GitHubCommitResponse;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient webClient;

    private final Retry retryInstance;

    public GitHubClientImpl(ApplicationConfig applicationConfig, Retry retryInstance) {
        this.retryInstance = retryInstance;
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.urls().gitHubBaseUrl())
            .build();
    }

    @Override
    public GitHubRepositoryResponse fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubRepositoryResponse.class)
            .retryWhen(retryInstance)
            .block();
    }

    @Override
    public List<GitHubCommitResponse> fetchCommit(String owner, String repo) {
        try {
            GitHubCommitResponse[] commits = webClient.get()
                .uri("repos/{owner}/{repo}/commits", owner, repo)
                .retrieve()
                .bodyToMono(GitHubCommitResponse[].class)
                .retryWhen(retryInstance)
                .block();

            return Arrays.stream(commits).toList();

        } catch (Exception ex) {
            return new ArrayList<>(0);
        }
    }
}

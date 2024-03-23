package edu.java.client;

import edu.java.client.dto.GitHubCommitResponse;
import edu.java.client.dto.GitHubRepositoryResponse;
import edu.java.configuration.ApplicationConfig;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient webClient;

    public GitHubClientImpl(ApplicationConfig applicationConfig) {
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
            .block();
    }

    @Override
    public GitHubCommitResponse fetchCommit(String owner, String repo) {
        try {
            GitHubCommitResponse.GitHubCommitItem[] commits = webClient.get()
                .uri("repos/{owner}/{repo}/commits", owner, repo)
                .retrieve()
                .bodyToMono(GitHubCommitResponse.GitHubCommitItem[].class)
                .block();

            return new GitHubCommitResponse(Arrays.stream(commits).toList());

        } catch (Exception ex) {
            return new GitHubCommitResponse(new ArrayList<>(0));
        }
    }
}

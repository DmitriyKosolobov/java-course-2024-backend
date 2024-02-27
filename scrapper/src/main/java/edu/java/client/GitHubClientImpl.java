package edu.java.client;

import edu.java.client.dto.GitHubRepositoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient webClient;

    @Autowired
    public GitHubClientImpl(WebClient gitHubWebClient) {
        this.webClient = gitHubWebClient;
    }

    @Override
    public Mono<GitHubRepositoryResponse> fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubRepositoryResponse.class);
    }
}

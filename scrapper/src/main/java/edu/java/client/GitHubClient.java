package edu.java.client;

import edu.java.client.dto.GitHubRepositoryResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<GitHubRepositoryResponse> fetchRepository(String owner, String repo);
}

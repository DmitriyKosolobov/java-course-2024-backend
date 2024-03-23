package edu.java.client;

import edu.java.client.dto.GitHubCommitResponse;
import edu.java.client.dto.GitHubRepositoryResponse;

public interface GitHubClient {
    GitHubRepositoryResponse fetchRepository(String owner, String repo);

    GitHubCommitResponse fetchCommit(String owner, String repo);
}

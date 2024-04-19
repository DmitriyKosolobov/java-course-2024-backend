package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.GitHubCommitResponse;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;

public interface GitHubClient {
    GitHubRepositoryResponse fetchRepository(String owner, String repo);

    GitHubCommitResponse fetchCommit(String owner, String repo);
}

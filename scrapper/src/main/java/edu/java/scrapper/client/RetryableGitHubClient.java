package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.GitHubCommitResponse;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;
import java.util.List;
import org.springframework.retry.support.RetryTemplate;

public class RetryableGitHubClient implements GitHubClient {

    private final RetryTemplate retryTemplate;

    private final GitHubClient gitHubClient;

    public RetryableGitHubClient(RetryTemplate retryTemplate, GitHubClient gitHubClient) {
        this.retryTemplate = retryTemplate;
        this.gitHubClient = gitHubClient;
    }

    @Override
    public GitHubRepositoryResponse fetchRepository(String owner, String repo) {
        return retryTemplate.execute((context -> gitHubClient.fetchRepository(owner, repo)));
    }

    @Override
    public List<GitHubCommitResponse> fetchCommit(String owner, String repo) {
        return retryTemplate.execute((context -> gitHubClient.fetchCommit(owner, repo)));
    }
}

package edu.java.scrapper.client.dto;

import java.util.List;

public record GitHubCommitResponse(
    List<GitHubCommitItem> items
) {
    public record GitHubCommitItem(
        String sha,
        Commit commit
    ) {
        public record Commit(
            String message
        ) {
        }
    }
}


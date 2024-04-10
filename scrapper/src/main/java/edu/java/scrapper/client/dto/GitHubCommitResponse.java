package edu.java.scrapper.client.dto;

public record GitHubCommitResponse(
    String sha,
    Commit commit
) {
    public record Commit(
        String message
    ) {
    }
}


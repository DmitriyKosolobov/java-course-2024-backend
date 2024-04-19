package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
    Long id,

    String name,

    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}

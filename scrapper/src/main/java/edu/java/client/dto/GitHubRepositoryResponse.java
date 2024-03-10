package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class GitHubRepositoryResponse {
    private long id;

    private String name;

    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}

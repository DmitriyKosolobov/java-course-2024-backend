package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class GitHubRepositoryResponse {
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt;
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}

package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowAnswerResponse(
    List<StackOverflowAnswerItem> items
) {
    public record StackOverflowAnswerItem(
        @JsonProperty("creation_date")
        OffsetDateTime creationDate
    ) {
    }
}

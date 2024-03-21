package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowQuestionItem(

    @JsonProperty("question_id")
    Long questionId,

    @JsonProperty("is_answered")
    boolean isAnswered,

    @JsonProperty("view_count")
    int viewCount,

    @JsonProperty("answer_count")
    int answerCount,

    int score,

    @JsonProperty("creation_date")
    OffsetDateTime creationDate,

    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivityDate
) {
}

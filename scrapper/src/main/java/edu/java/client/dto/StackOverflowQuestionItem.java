package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StackOverflowQuestionItem {

    @JsonProperty("question_id")
    private long questionId;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    @JsonProperty("view_count")
    private int viewCount;

    @JsonProperty("answer_count")
    private int answerCount;

    @JsonProperty("score")
    private int score;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @JsonProperty("last_activity_date")
    private OffsetDateTime lastActivityDate;

}

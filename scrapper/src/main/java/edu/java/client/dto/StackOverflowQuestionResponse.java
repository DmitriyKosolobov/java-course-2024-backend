package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class StackOverflowQuestionResponse {
    @JsonProperty("items")
    private List<StackOverflowQuestionItem> questions;
}

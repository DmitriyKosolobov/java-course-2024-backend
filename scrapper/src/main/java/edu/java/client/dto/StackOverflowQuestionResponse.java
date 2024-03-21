package edu.java.client.dto;

import java.util.List;

public record StackOverflowQuestionResponse(
    List<StackOverflowQuestionItem> items
) {
}

package edu.java.client.dto;

import java.util.List;
import lombok.Data;

@Data
public class StackOverflowQuestionResponse {
    private List<StackOverflowQuestionItem> items;
}

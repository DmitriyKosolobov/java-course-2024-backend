package edu.java.client;

import edu.java.client.dto.StackOverflowQuestionResponse;

public interface StackOverflowClient {
    StackOverflowQuestionResponse fetchQuestion(Long questionId);
}

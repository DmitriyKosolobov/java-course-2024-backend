package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.StackOverflowAnswerResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;

public interface StackOverflowClient {
    StackOverflowQuestionResponse fetchQuestion(Long questionId);

    StackOverflowAnswerResponse fetchAnswer(Long questionId);
}

package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.StackOverflowAnswerResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import org.springframework.retry.support.RetryTemplate;

public class RetryableStackOverflowClient implements StackOverflowClient {

    private final RetryTemplate retryTemplate;
    private final StackOverflowClient stackOverflowClient;

    public RetryableStackOverflowClient(RetryTemplate retryTemplate, StackOverflowClient stackOverflowClient) {
        this.retryTemplate = retryTemplate;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public StackOverflowQuestionResponse fetchQuestion(Long questionId) {
        return retryTemplate.execute((context -> stackOverflowClient.fetchQuestion(questionId)));
    }

    @Override
    public StackOverflowAnswerResponse fetchAnswer(Long questionId) {
        return retryTemplate.execute((context -> stackOverflowClient.fetchAnswer(questionId)));
    }
}

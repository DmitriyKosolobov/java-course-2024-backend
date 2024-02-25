package edu.java.client;

import edu.java.client.dto.StackOverflowQuestionResponse;
import reactor.core.publisher.Mono;

public interface StackOverflowClient {
    Mono<StackOverflowQuestionResponse> fetchQuestion(Long questionId);
}

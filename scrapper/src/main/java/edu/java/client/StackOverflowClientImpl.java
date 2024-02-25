package edu.java.client;

import edu.java.client.dto.StackOverflowQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient webClient;

    @Autowired
    public StackOverflowClientImpl(WebClient stackOverflowWebClient) {
        this.webClient = stackOverflowWebClient;
    }

    @Override
    public Mono<StackOverflowQuestionResponse> fetchQuestion(Long questionId) {
        return webClient.get()
            .uri("/questions/{questionId}?order=desc&sort=activity&site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class);
    }
}

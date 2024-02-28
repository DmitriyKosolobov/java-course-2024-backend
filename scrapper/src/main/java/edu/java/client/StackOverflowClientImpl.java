package edu.java.client;

import edu.java.client.dto.StackOverflowQuestionResponse;
import edu.java.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClientImpl(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.urls().stackOverflowBaseUrl())
            .build();
    }

    @Override
    public StackOverflowQuestionResponse fetchQuestion(Long questionId) {
        return webClient.get()
            .uri("2.3/questions/{questionId}?order=desc&sort=activity&site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class)
            .block();
    }
}

package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.StackOverflowAnswerResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient webClient;

    private final Retry retryInstance;

    public StackOverflowClientImpl(ApplicationConfig applicationConfig, Retry retryInstance) {
        this.retryInstance = retryInstance;
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
            .retryWhen(retryInstance)
            .block();
    }

    @Override
    public StackOverflowAnswerResponse fetchAnswer(Long questionId) {
        return webClient.get()
            .uri("2.3/questions/{questionId}/answers?order=desc&sort=activity&site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowAnswerResponse.class)
            .retryWhen(retryInstance)
            .block();
    }
}

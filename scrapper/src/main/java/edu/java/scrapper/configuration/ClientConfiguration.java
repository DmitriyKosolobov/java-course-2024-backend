package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.RetryableBotClient;
import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.util.retry.Retry;

@SuppressWarnings("MagicNumber")
@Configuration
public class ClientConfiguration {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Value("${bot.api.baseurl}")
    private String baseUrl;

    @Bean
    public BotClient botClient(RetryTemplate retryTemplate) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        BotClient client = factory.createClient(BotClient.class);
        return new RetryableBotClient(retryTemplate, client);
    }

//    @Bean
//    public GitHubClient gitHubClient(RetryTemplate retryTemplate) {
//        RestClient restClient = RestClient.builder().baseUrl(applicationConfig.urls().gitHubBaseUrl()).build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        GitHubClient client = factory.createClient(GitHubClient.class);
//        return new RetryableGitHubClient(retryTemplate, client);
//    }

//    @Bean
//    public StackOverflowClient stackOverflowClient(RetryTemplate retryTemplate) {
//        RestClient restClient = RestClient.builder().baseUrl(applicationConfig.urls().stackOverflowBaseUrl()).build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        StackOverflowClient client = factory.createClient(StackOverflowClient.class);
//        return new RetryableStackOverflowClient(retryTemplate, client);
//    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(getBackOffPolicy());

        CustomRetryPolicy retryPolicy = new CustomRetryPolicy(applicationConfig.retryCodes());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    private BackOffPolicy getBackOffPolicy() {
        return switch (applicationConfig.backOff()) {
            case CONSTANT -> new FixedBackOffPolicy();
            case LINEAR -> new LinearBackOffPolicy();
            case EXPONENTIAL -> new ExponentialBackOffPolicy();
        };
    }

    private Predicate<? super Throwable> buildFilter(List<Integer> retryCodes) {
        return e -> e instanceof WebClientResponseException
            && retryCodes.contains(((WebClientResponseException) e).getStatusCode().value());
    }

    @Bean
    public Retry retryInstance() {
        return switch (applicationConfig.backOff()) {
            case CONSTANT -> Retry.fixedDelay(3, Duration.ofMillis(1000L))
                .filter(buildFilter(applicationConfig.retryCodes()));

            case LINEAR -> LinearRetry.linearBackoff(3, Duration.ofMillis(1000L))
                .filter(buildFilter(applicationConfig.retryCodes()));

            case EXPONENTIAL -> Retry.backoff(3, Duration.ofMillis(1000L))
                .filter(buildFilter(applicationConfig.retryCodes()));
        };
    }

}

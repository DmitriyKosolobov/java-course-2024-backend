package edu.java.bot.configuration;

import edu.java.bot.client.RetryableScrapperClient;
import edu.java.bot.client.ScrapperClient;
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
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Value("${scrapper.api.baseurl}")
    private String baseUrl;

    @Bean
    public ScrapperClient scrapperClient(RetryTemplate retryTemplate) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        ScrapperClient client = factory.createClient(ScrapperClient.class);

        return new RetryableScrapperClient(retryTemplate, client);
    }

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
}

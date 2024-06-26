package edu.java.scrapper.configuration;

import edu.java.scrapper.enums.AccessType;
import edu.java.scrapper.enums.BackOffType;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Bean
    @NotNull
    Scheduler scheduler,
    BaseUrls urls,
    AccessType databaseAccessType,
    BackOffType backOff,
    List<Integer> retryCodes,
    String topicName,
    Boolean useQueue,
    String bootstrapServer
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record BaseUrls(String gitHubBaseUrl, String stackOverflowBaseUrl) {

    }
}

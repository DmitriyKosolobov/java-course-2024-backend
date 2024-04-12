package edu.java.scrapper.configuration;

import edu.java.scrapper.controller.dto.UpdateRequest;
import edu.java.scrapper.service.sender.ScrapperQueueProducer;
import edu.java.scrapper.service.sender.SenderNotification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class KafkaSenderConfiguration {

    @Bean
    public SenderNotification senderNotification(
        KafkaTemplate<String, UpdateRequest> kafkaTemplate, ApplicationConfig applicationConfig
    ) {
        return new ScrapperQueueProducer(kafkaTemplate, applicationConfig);
    }
}

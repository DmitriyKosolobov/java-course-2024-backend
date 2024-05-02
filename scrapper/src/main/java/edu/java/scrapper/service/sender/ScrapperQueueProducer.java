package edu.java.scrapper.service.sender;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.controller.dto.UpdateRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScrapperQueueProducer implements SenderNotification {

    private final KafkaTemplate<String, UpdateRequest> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public ScrapperQueueProducer(
        KafkaTemplate<String, UpdateRequest> kafkaTemplate,
        ApplicationConfig applicationConfig
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void send(UpdateRequest updateRequest) {
        kafkaTemplate.send(applicationConfig.topicName(), updateRequest);
    }
}

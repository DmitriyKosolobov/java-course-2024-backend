package edu.java.bot.service;

import edu.java.bot.controller.dto.UpdateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private final UpdateService updateService;

    public Listener(UpdateService updateService) {
        this.updateService = updateService;
    }

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${app.topic-name}", containerFactory = "containerFactory")
    public void kafka(UpdateRequest updateRequest) {
        updateService.handle(updateRequest);
    }
}

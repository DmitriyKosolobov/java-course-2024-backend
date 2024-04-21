package edu.java.bot.service;

import edu.java.bot.controller.dto.UpdateRequest;
import edu.java.bot.metric.MetricCounter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private final UpdateService updateService;

    private final MetricCounter metricCounter;

    public Listener(UpdateService updateService, MetricCounter metricCounter) {
        this.updateService = updateService;
        this.metricCounter = metricCounter;
    }

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${app.topic-name}", containerFactory = "containerFactory")
    public void kafka(UpdateRequest updateRequest) {
        updateService.handle(updateRequest);
        metricCounter.increment();
    }
}

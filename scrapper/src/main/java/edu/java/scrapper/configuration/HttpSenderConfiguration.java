package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.service.sender.HttpSender;
import edu.java.scrapper.service.sender.SenderNotification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpSenderConfiguration {
    @Bean
    public SenderNotification senderNotification(BotClient botClient) {
        return new HttpSender(botClient);
    }
}

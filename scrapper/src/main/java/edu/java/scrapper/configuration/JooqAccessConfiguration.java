package edu.java.scrapper.configuration;

import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.LinkUpdater;
import edu.java.scrapper.service.jooq.JooqChatService;
import edu.java.scrapper.service.jooq.JooqLinkService;
import edu.java.scrapper.service.jooq.JooqLinkUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public ChatService chatService(JooqChatRepository jooqChatRepository) {
        return new JooqChatService(jooqChatRepository);
    }

    @Bean
    public LinkService linkService(JooqLinkRepository jooqLinkRepository, JooqChatRepository jooqChatRepository) {
        return new JooqLinkService(jooqLinkRepository, jooqChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JooqLinkRepository jooqLinkRepository) {
        return new JooqLinkUpdater(jooqLinkRepository);
    }
}

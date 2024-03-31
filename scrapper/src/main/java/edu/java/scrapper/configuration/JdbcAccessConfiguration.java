package edu.java.scrapper.configuration;

import edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.LinkUpdater;
import edu.java.scrapper.service.jdbc.JdbcChatService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcLinkUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public ChatService chatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository, JdbcChatRepository jdbcChatRepository) {
        return new JdbcLinkService(jdbcLinkRepository, jdbcChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JdbcLinkRepository jdbcLinkRepository) {
        return new JdbcLinkUpdater(jdbcLinkRepository);
    }
}

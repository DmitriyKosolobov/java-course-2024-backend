package edu.java.scrapper.service.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JooqChatServiceTest extends IntegrationTest {
    @Autowired
    private ChatService chatService;

    @DynamicPropertySource
    public static void setJdbcAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jooq");
    }

    @Test
    @Transactional
    @Rollback
    void registerTest() {

        Assertions.assertDoesNotThrow(() -> {
            chatService.register(5L);
        });

        Assertions.assertThrows(ExistChatException.class, () -> {
            chatService.register(5L);
        });
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {

        Assertions.assertDoesNotThrow(() -> {
            chatService.register(5L);
        });

        Assertions.assertDoesNotThrow(() -> {
            chatService.unregister(5L);
        });

        Assertions.assertThrows(NotFoundChatException.class, () -> {
            chatService.unregister(5L);
        });
    }
}

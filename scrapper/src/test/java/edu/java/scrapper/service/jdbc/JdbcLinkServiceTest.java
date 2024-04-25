package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;

@SpringBootTest
public class JdbcLinkServiceTest extends IntegrationTest {

    @DynamicPropertySource
    public static void setJdbcAccessType(DynamicPropertyRegistry registry){
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @Autowired
    private ChatService chatService;

    @Autowired
    private LinkService linkService;

    @Test
    @Transactional
    @Rollback
    void addTest(){
        Assertions.assertThrows(NotFoundChatException.class, () -> {
            linkService.add(5L, URI.create("https://github.com/dashboard"));
        });

        Assertions.assertDoesNotThrow(() -> {
            chatService.register(5L);
        });
        Assertions.assertDoesNotThrow(() -> {
            linkService.add(5L, URI.create("https://github.com/dashboard"));
        });

        Assertions.assertThrows(ExistLinkException.class, () -> {
            linkService.add(5L, URI.create("https://github.com/dashboard"));
        });

    }

    @Test
    @Transactional
    @Rollback
    void removeTest(){
        Assertions.assertThrows(NotFoundChatException.class, () -> {
            linkService.remove(5L, URI.create("https://github.com/dashboard"));
        });
        Assertions.assertDoesNotThrow(() -> {
            chatService.register(5L);
        });
        Assertions.assertThrows(NotFoundLinkException.class, () -> {
            linkService.remove(5L, URI.create("https://github.com/dashboard"));
        });
        Assertions.assertDoesNotThrow(() -> {
            linkService.add(5L, URI.create("https://github.com/dashboard"));
        });

        Assertions.assertDoesNotThrow(() -> {
            linkService.remove(5L, URI.create("https://github.com/dashboard"));
        });
    }

    @Test
    @Transactional
    @Rollback
    void listAllTest(){
        Assertions.assertThrows(NotFoundChatException.class, () -> {
            linkService.listAll(5L);
        });
        Assertions.assertDoesNotThrow(() -> {
            chatService.register(5L);
        });
        Assertions.assertDoesNotThrow(() -> {
            linkService.listAll(5L);
        });
    }

}

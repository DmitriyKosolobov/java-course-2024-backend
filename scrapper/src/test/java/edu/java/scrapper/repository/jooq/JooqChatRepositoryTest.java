package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.dto.Chat;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JooqChatRepositoryTest extends IntegrationTest {

    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat addedChat = jooqChatRepository.add(7L);
        List<Chat> chats = jooqChatRepository.findAll();

        Assertions.assertEquals(7L, addedChat.chatId());
        Assertions.assertEquals(1, chats.size());
        Assertions.assertEquals(7L, chats.getFirst().chatId());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jooqChatRepository.add(7L);
        int res1 = jooqChatRepository.remove(7L);
        int res2 = jooqChatRepository.remove(9L);
        List<Chat> chats = jooqChatRepository.findAll();

        Assertions.assertEquals(0, chats.size());
        Assertions.assertEquals(1, res1);
        Assertions.assertEquals(0, res2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        jooqChatRepository.add(8L);
        jooqChatRepository.add(5L);
        List<Chat> chats = jooqChatRepository.findAll();

        Assertions.assertEquals(2, chats.size());
        Assertions.assertEquals(8L, chats.getFirst().chatId());
        Assertions.assertEquals(5L, chats.getLast().chatId());
    }

    @Test
    @Transactional
    @Rollback
    void getChatIdByTgChatId() {
        jooqChatRepository.add(5L);
        Long chatId1 = jooqChatRepository.getChatIdByTgChatId(5L);
        Long chatId2 = jooqChatRepository.getChatIdByTgChatId(7L);

        Assertions.assertNotNull(chatId1);
        Assertions.assertNull(chatId2);
    }
}

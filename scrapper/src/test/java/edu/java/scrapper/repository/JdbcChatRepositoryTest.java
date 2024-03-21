package edu.java.scrapper.repository;

import edu.java.repository.ChatRepository;
import edu.java.repository.dto.Chat;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat addedChat = chatRepository.add(7L);
        List<Chat> chats = chatRepository.findAll();

        Assertions.assertEquals(7L, addedChat.chatId());
        Assertions.assertEquals(1, chats.size());
        Assertions.assertEquals(7L, chats.getFirst().chatId());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        chatRepository.add(7L);
        int res1 = chatRepository.remove(7L);
        int res2 = chatRepository.remove(9L);
        List<Chat> chats = chatRepository.findAll();

        Assertions.assertEquals(0, chats.size());
        Assertions.assertEquals(1, res1);
        Assertions.assertEquals(0, res2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        chatRepository.add(8L);
        chatRepository.add(5L);
        List<Chat> chats = chatRepository.findAll();

        Assertions.assertEquals(2, chats.size());
        Assertions.assertEquals(8L, chats.getFirst().chatId());
        Assertions.assertEquals(5L, chats.getLast().chatId());
    }

    @Test
    @Transactional
    @Rollback
    void getChatIdByTgChatId() {
        chatRepository.add(5L);
        Long chatId1 = chatRepository.getChatIdByTgChatId(5L);
        Long chatId2 = chatRepository.getChatIdByTgChatId(7L);

        Assertions.assertNotNull(chatId1);
        Assertions.assertNull(chatId2);
    }
}

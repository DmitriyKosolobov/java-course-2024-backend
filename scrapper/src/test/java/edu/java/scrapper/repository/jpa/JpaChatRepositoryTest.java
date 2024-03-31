package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {

        Chat addedChat = jpaChatRepository.save(new Chat(7L));
        List<Chat> chats = jpaChatRepository.findAll();

        Assertions.assertEquals(7L, addedChat.getTgChatId());
        Assertions.assertEquals(1, chats.size());
        Assertions.assertEquals(7L, chats.getFirst().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jpaChatRepository.save(new Chat(7L));
        jpaChatRepository.deleteByTgChatId(7L);
        jpaChatRepository.deleteByTgChatId(9L);

        List<Chat> chats = jpaChatRepository.findAll();

        Assertions.assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        jpaChatRepository.save(new Chat(8L));
        jpaChatRepository.save(new Chat(5L));

        List<Chat> chats = jpaChatRepository.findAll();

        Assertions.assertEquals(2, chats.size());
        Assertions.assertEquals(8L, chats.getFirst().getTgChatId());
        Assertions.assertEquals(5L, chats.getLast().getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    void findByTgChatIdTest() {
        Chat chat = new Chat(5L);
        jpaChatRepository.save(chat);

        Optional<Chat> optionalChat1 = jpaChatRepository.findByTgChatId(5L);
        Optional<Chat> optionalChat2 = jpaChatRepository.findByTgChatId(2L);

        Assertions.assertTrue(optionalChat1.isPresent());
        Assertions.assertEquals(5L, optionalChat1.get().getTgChatId());
        Assertions.assertTrue(optionalChat2.isEmpty());
    }

}

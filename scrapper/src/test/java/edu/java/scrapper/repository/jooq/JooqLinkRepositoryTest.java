package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Autowired
    private JooqLinkRepository jooqLinkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = jooqChatRepository.add(5L).id();
        String url = "https://github.com/dashboard";
        Link addedLink = jooqLinkRepository.add(chatId, url);
        List<Link> links = jooqLinkRepository.findAllByChatId(chatId);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(url, links.getFirst().url());
        Assertions.assertEquals(url, addedLink.url());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId1 = jooqChatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        jooqLinkRepository.add(chatId1, url1);

        Long chatId2 = jooqChatRepository.add(3L).id();

        Link res1 = jooqLinkRepository.remove(chatId2, url1);
        Link res2 = jooqLinkRepository.remove(chatId1, url1);
        Link res3 = jooqLinkRepository.remove(chatId2, url1);

        List<Link> links = jooqLinkRepository.findAllByChatId(chatId1);
        List<Link> allLinks = jooqLinkRepository.findAll();

        Assertions.assertEquals(0, allLinks.size());
        Assertions.assertEquals(0, links.size());
        Assertions.assertNull(res1);
        Assertions.assertEquals(url1, res2.url());
        Assertions.assertNull(res3);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        Long chatId1 = jooqChatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        String url2 = "https://stackoverflow.com/";
        jooqLinkRepository.add(chatId1, url1);
        jooqLinkRepository.add(chatId1, url2);

        Long chatId2 = jooqChatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        jooqLinkRepository.add(chatId2, url3);

        List<Link> links = jooqLinkRepository.findAll();

        Assertions.assertEquals(3, links.size());
        Assertions.assertEquals(url1, links.get(0).url());
        Assertions.assertEquals(url2, links.get(1).url());
        Assertions.assertEquals(url3, links.get(2).url());
    }

    @Test
    @Transactional
    @Rollback
    void findAllByChatIdTest() {
        Long chatId1 = jooqChatRepository.add(5L).id();
        String url1 = "https://stackoverflow.com/";
        String url2 = "https://github.com/dashboard";
        jooqLinkRepository.add(chatId1, url1);
        jooqLinkRepository.add(chatId1, url2);

        Long chatId2 = jooqChatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        jooqLinkRepository.add(chatId2, url3);

        List<Link> links = jooqLinkRepository.findAllByChatId(chatId1);
        List<Link> emptyList = jooqLinkRepository.findAllByChatId(1000L);

        Assertions.assertEquals(2, links.size());
        Assertions.assertEquals(url1, links.getFirst().url());
        Assertions.assertEquals(url2, links.getLast().url());
        Assertions.assertEquals(0, emptyList.size());
    }

}

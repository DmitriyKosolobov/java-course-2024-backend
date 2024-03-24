package edu.java.scrapper.repository;

import edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.domain.jdbc.dto.Link;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = jdbcChatRepository.add(5L).id();
        String url = "https://github.com/dashboard";
        Link addedLink = jdbcLinkRepository.add(chatId, url);
        List<Link> links = jdbcLinkRepository.findAllByChatId(chatId);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(url, links.getFirst().url());
        Assertions.assertEquals(url, addedLink.url());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId1 = jdbcChatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        jdbcLinkRepository.add(chatId1, url1);

        Long chatId2 = jdbcChatRepository.add(3L).id();

        Link res1 = jdbcLinkRepository.remove(chatId1, url1);
        Link res2 = jdbcLinkRepository.remove(chatId2, url1);

        List<Link> links = jdbcLinkRepository.findAllByChatId(chatId1);
        List<Link> allLinks = jdbcLinkRepository.findAll();

        Assertions.assertEquals(0, allLinks.size());
        Assertions.assertEquals(0, links.size());
        Assertions.assertEquals(url1, res1.url());
        Assertions.assertNull(res2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        Long chatId1 = jdbcChatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        String url2 = "https://stackoverflow.com/";
        jdbcLinkRepository.add(chatId1, url1);
        jdbcLinkRepository.add(chatId1, url2);

        Long chatId2 = jdbcChatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        jdbcLinkRepository.add(chatId2, url3);

        List<Link> links = jdbcLinkRepository.findAll();

        Assertions.assertEquals(3, links.size());
        Assertions.assertEquals(url1, links.get(0).url());
        Assertions.assertEquals(url2, links.get(1).url());
        Assertions.assertEquals(url3, links.get(2).url());
    }

    @Test
    @Transactional
    @Rollback
    void findAllByChatIdTest() {
        Long chatId1 = jdbcChatRepository.add(5L).id();
        String url1 = "https://stackoverflow.com/";
        String url2 = "https://github.com/dashboard";
        jdbcLinkRepository.add(chatId1, url1);
        jdbcLinkRepository.add(chatId1, url2);

        Long chatId2 = jdbcChatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        jdbcLinkRepository.add(chatId2, url3);

        List<Link> links = jdbcLinkRepository.findAllByChatId(chatId1);
        List<Link> emptyList = jdbcLinkRepository.findAllByChatId(1000L);

        Assertions.assertEquals(2, links.size());
        Assertions.assertEquals(url1, links.getFirst().url());
        Assertions.assertEquals(url2, links.getLast().url());
        Assertions.assertEquals(0, emptyList.size());
    }

}

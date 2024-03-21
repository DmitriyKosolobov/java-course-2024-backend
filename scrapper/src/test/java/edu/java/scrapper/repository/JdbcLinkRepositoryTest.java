package edu.java.scrapper.repository;

import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.repository.dto.Link;
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
    private ChatRepository chatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = chatRepository.add(5L).id();
        String url = "https://github.com/dashboard";
        Link addedLink = linkRepository.add(chatId, url);
        List<Link> links = linkRepository.findAllByChatId(chatId);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(url, links.getFirst().url());
        Assertions.assertEquals(url, addedLink.url());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId1 = chatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        linkRepository.add(chatId1, url1);

        Long chatId2 = chatRepository.add(3L).id();

        Link res1 = linkRepository.remove(chatId1, url1);
        Link res2 = linkRepository.remove(chatId2, url1);

        List<Link> links = linkRepository.findAllByChatId(chatId1);
        List<Link> allLinks = linkRepository.findAll();

        Assertions.assertEquals(0, allLinks.size());
        Assertions.assertEquals(0, links.size());
        Assertions.assertEquals(url1, res1.url());
        Assertions.assertNull(res2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        Long chatId1 = chatRepository.add(5L).id();
        String url1 = "https://github.com/dashboard";
        String url2 = "https://stackoverflow.com/";
        linkRepository.add(chatId1, url1);
        linkRepository.add(chatId1, url2);

        Long chatId2 = chatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        linkRepository.add(chatId2, url3);

        List<Link> links = linkRepository.findAll();

        Assertions.assertEquals(3, links.size());
        Assertions.assertEquals(url1, links.get(0).url());
        Assertions.assertEquals(url2, links.get(1).url());
        Assertions.assertEquals(url3, links.get(2).url());
    }

    @Test
    @Transactional
    @Rollback
    void findAllByChatIdTest() {
        Long chatId1 = chatRepository.add(5L).id();
        String url1 = "https://stackoverflow.com/";
        String url2 = "https://github.com/dashboard";
        linkRepository.add(chatId1, url1);
        linkRepository.add(chatId1, url2);

        Long chatId2 = chatRepository.add(9L).id();
        String url3 = "https://edu.tinkoff.ru/";
        linkRepository.add(chatId2, url3);

        List<Link> links = linkRepository.findAllByChatId(chatId1);
        List<Link> emptyList = linkRepository.findAllByChatId(1000L);

        Assertions.assertEquals(2, links.size());
        Assertions.assertEquals(url1, links.getFirst().url());
        Assertions.assertEquals(url2, links.getLast().url());
        Assertions.assertEquals(0, emptyList.size());
    }

}

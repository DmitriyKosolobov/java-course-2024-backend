package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import edu.java.scrapper.domain.jpa.entity.Link;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat chat = jpaChatRepository.save(new Chat(7L));
        String url = "https://github.com/dashboard";
        Link link = new Link(url, chat);

        Link addedLink = jpaLinkRepository.save(link);
        List<Link> links = jpaLinkRepository.findAll();

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(url, links.getFirst().getUrl());
        Assertions.assertEquals(url, addedLink.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Chat chat = jpaChatRepository.save(new Chat(5L));
        Link link = jpaLinkRepository.save(new Link("https://github.com/dashboard", chat));

        link.deleteChat(chat);
        if (link.getChats().isEmpty()) {
            jpaLinkRepository.delete(link);
        }

        List<Link> links = jpaLinkRepository.findAll();

        Assertions.assertEquals(0, chat.getLinks().size());
        Assertions.assertEquals(0, links.size());
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        Chat chat1 = jpaChatRepository.save(new Chat(5L));
        Link link1 = jpaLinkRepository.save(new Link("https://github.com/dashboard", chat1));
        Link link2 = jpaLinkRepository.save(new Link("https://stackoverflow.com/", chat1));
        jpaLinkRepository.save(link1);
        jpaLinkRepository.save(link2);

        Chat chat2 = jpaChatRepository.save(new Chat(9L));
        Link link3 = jpaLinkRepository.save(new Link("https://edu.tinkoff.ru/", chat1));
        jpaLinkRepository.save(link3);

        List<Link> links = jpaLinkRepository.findAll();

        Assertions.assertEquals(3, links.size());
        Assertions.assertEquals("https://github.com/dashboard", links.get(0).getUrl());
        Assertions.assertEquals("https://stackoverflow.com/", links.get(1).getUrl());
        Assertions.assertEquals("https://edu.tinkoff.ru/", links.get(2).getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void findByUrlTest() {
        Chat chat = jpaChatRepository.save(new Chat(5L));
        Link link = jpaLinkRepository.save(new Link("https://github.com/dashboard", chat));
        jpaLinkRepository.save(link);

        Optional<Link> optionalLink = jpaLinkRepository.findByUrl("https://github.com/dashboard");

        Assertions.assertTrue(optionalLink.isPresent());
        Assertions.assertEquals("https://github.com/dashboard", optionalLink.get().getUrl());
    }

}

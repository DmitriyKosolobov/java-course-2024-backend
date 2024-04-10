package edu.java.scrapper.service.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import edu.java.scrapper.domain.jpa.entity.Link;
import edu.java.scrapper.service.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaLinkUpdaterTest extends IntegrationTest {

    @Autowired
    private LinkUpdater linkUpdater;
    @Autowired
    private JpaChatRepository jpaChatRepository;
    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @DynamicPropertySource
    public static void setJdbcAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        String url = "https://github.com/dashboard";
        OffsetDateTime dataTime = OffsetDateTime.now().minusSeconds(5L);

        Chat chat = jpaChatRepository.save(new Chat(7L));
        Link link = new Link(url, chat);
        link.setLastCheckTime(dataTime);

        link = jpaLinkRepository.save(link);

        int res = linkUpdater.update(link.getId());

        List<Link> links = jpaLinkRepository.findAll();

        Assertions.assertEquals(1, res);
        Assertions.assertTrue(links.getFirst().getLastCheckTime().isAfter(dataTime));
    }

    @Test
    @Transactional
    @Rollback
    void listAllOldCheckedLinksTest() {
        String url1 = "https://github.com/dashboard";
        String url2 = "https://stackoverflow.com/";

        Chat chat = jpaChatRepository.save(new Chat(5L));
        Link link1 = new Link(url1, chat);
        Link link2 = new Link(url2, chat);

        link1.setLastCheckTime(OffsetDateTime.now().minusSeconds(6L));
        link2.setLastCheckTime(OffsetDateTime.now());

        jpaLinkRepository.save(link1);
        jpaLinkRepository.save(link2);

        List<edu.java.scrapper.domain.dto.Link> links =
            (List<edu.java.scrapper.domain.dto.Link>) linkUpdater.listAllOldCheckedLinks(5L);

        Assertions.assertEquals(1, links.size());
        Assertions.assertEquals(url1, links.getFirst().url());
    }

    @Test
    @Transactional
    @Rollback
    void listAllTgChatIdByLinkIdTest() {
        Chat chat1 = jpaChatRepository.save(new Chat(5L));
        String url1 = "https://stackoverflow.com/";
        String url2 = "https://github.com/dashboard";
        Link link1 = new Link(url1, chat1);
        Link link2 = new Link(url2, chat1);
        jpaLinkRepository.save(link2);

        Chat chat2 = jpaChatRepository.save(new Chat(9L));
        link1.addChat(chat2);
        Long linkId = jpaLinkRepository.save(link1).getId();

        List<Long> tgChatIds = linkUpdater.listAllTgChatIdByLinkId(linkId);

        Assertions.assertEquals(2, tgChatIds.size());
        Assertions.assertEquals(5L, tgChatIds.getFirst());
        Assertions.assertEquals(9L, tgChatIds.getLast());
    }
}

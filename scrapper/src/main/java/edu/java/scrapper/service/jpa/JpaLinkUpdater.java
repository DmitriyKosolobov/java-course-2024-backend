package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import edu.java.scrapper.service.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JpaLinkUpdater implements LinkUpdater {
    private final JpaLinkRepository jpaLinkRepository;

    public JpaLinkUpdater(JpaLinkRepository jpaLinkRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @Override
    public int update(Long linkId) {
        Optional<edu.java.scrapper.domain.jpa.entity.Link> optionalLink = jpaLinkRepository.findById(linkId);
        if (optionalLink.isEmpty()) {
            return 0;
        } else {
            edu.java.scrapper.domain.jpa.entity.Link link = optionalLink.get();
            link.setLastCheckTime(OffsetDateTime.now());
            jpaLinkRepository.save(link);
            return 1;
        }
    }

    @Override
    public Collection<Link> listAllOldCheckedLinks(Long forceCheckDelay) {
        return jpaLinkRepository.findOldCheckedLinks(forceCheckDelay).stream()
            .map(link -> new Link(
                link.getId(),
                link.getUrl(),
                link.getLastCheckTime(),
                link.getAnswersCount(),
                link.getCommitsCount()
            ))
            .toList();
    }

    @Override
    public List<Long> listAllTgChatIdByLinkId(Long linkId) {
        Optional<edu.java.scrapper.domain.jpa.entity.Link> optionalLink = jpaLinkRepository.findById(linkId);
        if (optionalLink.isEmpty()) {
            return new ArrayList<>(0);
        } else {
            edu.java.scrapper.domain.jpa.entity.Link link = optionalLink.get();
            return link.getChats().stream().map(Chat::getTgChatId).toList();
        }
    }
}

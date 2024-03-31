package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import edu.java.scrapper.service.LinkUpdater;
import java.util.Collection;
import java.util.List;

public class JooqLinkUpdater implements LinkUpdater {

    private final JooqLinkRepository jooqLinkRepository;

    public JooqLinkUpdater(JooqLinkRepository jooqLinkRepository) {
        this.jooqLinkRepository = jooqLinkRepository;
    }

    @Override
    public int update(Long linkId) {
        return jooqLinkRepository.updateLastCheckTime(linkId);
    }

    @Override
    public Collection<Link> listAllOldCheckedLinks(Long forceCheckDelay) {
        return jooqLinkRepository.findOldCheckedLinks(forceCheckDelay);
    }

    @Override
    public List<Long> listAllTgChatIdByLinkId(Long linkId) {
        return jooqLinkRepository.findAllTgChatIdByLinkId(linkId);
    }
}

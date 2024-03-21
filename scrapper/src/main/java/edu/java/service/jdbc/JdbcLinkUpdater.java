package edu.java.service.jdbc;

import edu.java.repository.LinkRepository;
import edu.java.repository.dto.Link;
import edu.java.service.LinkUpdater;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkRepository linkRepository;

    public JdbcLinkUpdater(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public int update(Long linkId) {
        return linkRepository.updateLastCheckTime(linkId);
    }

    @Override
    public Collection<Link> listAllOldCheckedLinks() {
        return linkRepository.findOldCheckedLinks();
    }

    @Override
    public List<Long> listAllTgChatIdByLinkId(Long linkId) {
        return linkRepository.findAllTgChatIdByLinkId(linkId);
    }
}

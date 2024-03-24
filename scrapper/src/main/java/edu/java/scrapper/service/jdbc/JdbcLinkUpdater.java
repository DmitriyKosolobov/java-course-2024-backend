package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.domain.jdbc.dto.Link;
import edu.java.scrapper.service.LinkUpdater;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdater implements LinkUpdater {

    private final JdbcLinkRepository jdbcLinkRepository;

    public JdbcLinkUpdater(JdbcLinkRepository jdbcLinkRepository) {
        this.jdbcLinkRepository = jdbcLinkRepository;
    }

    @Override
    public int update(Long linkId) {
        return jdbcLinkRepository.updateLastCheckTime(linkId);
    }

    @Override
    public Collection<Link> listAllOldCheckedLinks() {
        return jdbcLinkRepository.findOldCheckedLinks();
    }

    @Override
    public List<Long> listAllTgChatIdByLinkId(Long linkId) {
        return jdbcLinkRepository.findAllTgChatIdByLinkId(linkId);
    }
}
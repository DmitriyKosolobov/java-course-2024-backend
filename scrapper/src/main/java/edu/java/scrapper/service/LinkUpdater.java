package edu.java.scrapper.service;

import edu.java.scrapper.domain.jdbc.dto.Link;
import java.util.Collection;
import java.util.List;

public interface LinkUpdater {
    int update(Long linkId);

    Collection<Link> listAllOldCheckedLinks();

    List<Long> listAllTgChatIdByLinkId(Long linkId);
}

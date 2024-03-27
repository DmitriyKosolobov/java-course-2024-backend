package edu.java.scrapper.service;

import edu.java.scrapper.domain.dto.Link;
import java.util.Collection;
import java.util.List;

public interface LinkUpdater {
    int update(Long linkId);

    Collection<Link> listAllOldCheckedLinks(Long forceCheckDelay);

    List<Long> listAllTgChatIdByLinkId(Long linkId);
}

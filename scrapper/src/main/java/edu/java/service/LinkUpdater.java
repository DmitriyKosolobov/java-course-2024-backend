package edu.java.service;

import edu.java.repository.dto.Link;
import java.util.Collection;
import java.util.List;

public interface LinkUpdater {
    int update(Long linkId);

    Collection<Link> listAllOldCheckedLinks();

    List<Long> listAllTgChatIdByLinkId(Long linkId);
}

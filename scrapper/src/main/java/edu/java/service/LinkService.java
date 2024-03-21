package edu.java.service;

import edu.java.exception.ExistLinkException;
import edu.java.exception.NotFoundChatException;
import edu.java.exception.NotFoundLinkException;
import edu.java.repository.dto.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException;

    Link remove(Long tgChatId, URI url) throws NotFoundChatException, NotFoundLinkException;

    Collection<Link> listAll(Long tgChatId) throws NotFoundChatException;
}

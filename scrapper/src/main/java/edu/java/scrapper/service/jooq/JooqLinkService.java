package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.domain.jooq.JooqLinkRepository;
import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class JooqLinkService implements LinkService {

    private final JooqLinkRepository jooqLinkRepository;
    private final JooqChatRepository jooqChatRepository;

    public JooqLinkService(JooqLinkRepository jooqLinkRepository, JooqChatRepository jooqChatRepository) {
        this.jooqLinkRepository = jooqLinkRepository;
        this.jooqChatRepository = jooqChatRepository;
    }

    @Override
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        Long chatId = jooqChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }

        try {
            return jooqLinkRepository.add(chatId, url.toString());
        } catch (Exception e) {
            throw new ExistLinkException();
        }
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws NotFoundChatException, NotFoundLinkException {
        Long chatId = jooqChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        Link link = jooqLinkRepository.remove(chatId, url.toString());
        if (link == null) {
            throw new NotFoundLinkException();
        } else {
            return link;
        }
    }

    @Override
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        Long chatId = jooqChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        return jooqLinkRepository.findAllByChatId(chatId);
    }
}

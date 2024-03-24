package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import edu.java.scrapper.domain.jdbc.dto.Link;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcChatRepository jdbcChatRepository;

    public JdbcLinkService(JdbcLinkRepository jdbcLinkRepository, JdbcChatRepository jdbcChatRepository) {
        this.jdbcLinkRepository = jdbcLinkRepository;
        this.jdbcChatRepository = jdbcChatRepository;
    }

    @Override
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        Long chatId = jdbcChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }

        try {
            return jdbcLinkRepository.add(chatId, url.toString());
        } catch (Exception e) {
            throw new ExistLinkException();
        }
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws NotFoundChatException, NotFoundLinkException {
        Long chatId = jdbcChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        Link link = jdbcLinkRepository.remove(chatId, url.toString());
        if (link == null) {
            throw new NotFoundLinkException();
        } else {
            return link;
        }
    }

    @Override
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        Long chatId = jdbcChatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        return jdbcLinkRepository.findAllByChatId(chatId);
    }
}

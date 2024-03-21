package edu.java.service.jdbc;

import edu.java.exception.ExistLinkException;
import edu.java.exception.NotFoundChatException;
import edu.java.exception.NotFoundLinkException;
import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.repository.dto.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;

    public JdbcLinkService(LinkRepository linkRepository, ChatRepository chatRepository) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        Long chatId = chatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }

        try {
            return linkRepository.add(chatId, url.toString());
        } catch (Exception e) {
            throw new ExistLinkException();
        }
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws NotFoundChatException, NotFoundLinkException {
        Long chatId = chatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        Link link = linkRepository.remove(chatId, url.toString());
        if (link == null) {
            throw new NotFoundLinkException();
        } else {
            return link;
        }
    }

    @Override
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        Long chatId = chatRepository.getChatIdByTgChatId(tgChatId);
        if (chatId == null) {
            throw new NotFoundChatException();
        }
        return linkRepository.findAllByChatId(chatId);
    }
}

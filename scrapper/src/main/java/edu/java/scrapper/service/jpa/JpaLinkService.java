package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.JpaLinkRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    public JpaLinkService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    @Transactional
    public Link add(Long tgChatId, URI url) throws NotFoundChatException, ExistLinkException {
        Optional<Chat> optionalChat = jpaChatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isEmpty()) {
            throw new NotFoundChatException();

        } else {

            Chat chat = optionalChat.get();
            Optional<edu.java.scrapper.domain.jpa.entity.Link> optionalLink =
                jpaLinkRepository.findByUrl(url.toString());
            edu.java.scrapper.domain.jpa.entity.Link link;

            if (optionalLink.isEmpty()) {
                link = new edu.java.scrapper.domain.jpa.entity.Link(url.toString(), chat);
            } else {

                link = optionalLink.get();

                if (link.getChats().contains(chat) || chat.getLinks().contains(link)) {
                    throw new ExistLinkException();
                }

                link.addChat(chat);
            }

            try {
                link = jpaLinkRepository.save(link);
                return new Link(
                    link.getId(),
                    link.getUrl(),
                    link.getLastCheckTime(),
                    link.getAnswersCount(),
                    link.getCommitsCount()
                );

            } catch (DuplicateKeyException e) {
                throw new ExistLinkException();
            }
        }
    }

    @Override
    @Transactional
    public Link remove(Long tgChatId, URI url) throws NotFoundChatException, NotFoundLinkException {
        Optional<Chat> optionalChat = jpaChatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isEmpty()) {
            throw new NotFoundChatException();

        } else {

            Optional<edu.java.scrapper.domain.jpa.entity.Link> optionalLink =
                jpaLinkRepository.findByUrl(url.toString());
            if (optionalLink.isEmpty()) {
                throw new NotFoundLinkException();
            } else {
                Chat chat = optionalChat.get();
                edu.java.scrapper.domain.jpa.entity.Link link = optionalLink.get();

                if (!chat.getLinks().contains(link)) {
                    throw new NotFoundLinkException();
                }

                link.deleteChat(chat);
                if (link.getChats().isEmpty()) {
                    jpaLinkRepository.delete(link);
                } else {
                    jpaLinkRepository.save(link);
                }

                return new Link(
                    link.getId(),
                    link.getUrl(),
                    link.getLastCheckTime(),
                    link.getAnswersCount(),
                    link.getCommitsCount()
                );
            }
        }
    }

    @Override
    public Collection<Link> listAll(Long tgChatId) throws NotFoundChatException {
        Optional<Chat> optionalChat = jpaChatRepository.findByTgChatId(tgChatId);
        if (optionalChat.isEmpty()) {
            throw new NotFoundChatException();

        } else {
            Chat chat = optionalChat.get();
            return chat.getLinks().stream()
                .map(link -> new Link(
                    link.getId(),
                    link.getUrl(),
                    link.getLastCheckTime(),
                    link.getAnswersCount(),
                    link.getCommitsCount()
                )).toList();
        }
    }
}

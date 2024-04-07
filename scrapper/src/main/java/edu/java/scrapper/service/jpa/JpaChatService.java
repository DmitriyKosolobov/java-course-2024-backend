package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.jpa.JpaChatRepository;
import edu.java.scrapper.domain.jpa.entity.Chat;
import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.service.ChatService;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class JpaChatService implements ChatService {

    private final JpaChatRepository jpaChatRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    @Transactional
    public void register(Long tgChatId) throws ExistChatException {
        try {
            Chat chat = new Chat(tgChatId);
            jpaChatRepository.save(chat);
        } catch (Exception e) {
            throw new ExistChatException();
        }
    }

    @Override
    @Transactional
    public void unregister(Long tgChatId) throws NotFoundChatException {
        Optional<Chat> chat = jpaChatRepository.findByTgChatId(tgChatId);
        if (chat.isPresent()) {
            jpaChatRepository.deleteByTgChatId(tgChatId);
        } else {
            throw new NotFoundChatException();
        }
    }
}

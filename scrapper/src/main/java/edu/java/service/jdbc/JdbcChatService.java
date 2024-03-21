package edu.java.service.jdbc;

import edu.java.exception.ExistChatException;
import edu.java.exception.NotFoundChatException;
import edu.java.repository.ChatRepository;
import edu.java.service.ChatService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;

    public JdbcChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(Long tgChatId) throws ExistChatException {
        try {
            chatRepository.add(tgChatId);
        } catch (DuplicateKeyException e) {
            throw new ExistChatException();
        }
    }

    @Override
    public void unregister(Long tgChatId) throws NotFoundChatException {
        int result = chatRepository.remove(tgChatId);
        if (result == 0) {
            throw new NotFoundChatException();
        }
    }
}

package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.jooq.JooqChatRepository;
import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.service.ChatService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class JooqChatService implements ChatService {

    private final JooqChatRepository jooqChatRepository;

    public JooqChatService(JooqChatRepository jooqChatRepository) {
        this.jooqChatRepository = jooqChatRepository;
    }

    @Override
    public void register(Long tgChatId) throws ExistChatException {
        try {
            jooqChatRepository.add(tgChatId);
        } catch (DuplicateKeyException e) {
            throw new ExistChatException();
        }
    }

    @Override
    public void unregister(Long tgChatId) throws NotFoundChatException {
        int result = jooqChatRepository.remove(tgChatId);
        if (result == 0) {
            throw new NotFoundChatException();
        }
    }
}

package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.service.ChatService;
import org.springframework.dao.DuplicateKeyException;

public class JdbcChatService implements ChatService {

    private final JdbcChatRepository jdbcChatRepository;

    public JdbcChatService(JdbcChatRepository jdbcChatRepository) {
        this.jdbcChatRepository = jdbcChatRepository;
    }

    @Override
    public void register(Long tgChatId) throws ExistChatException {
        try {
            jdbcChatRepository.add(tgChatId);
        } catch (DuplicateKeyException e) {
            throw new ExistChatException();
        }
    }

    @Override
    public void unregister(Long tgChatId) throws NotFoundChatException {
        int result = jdbcChatRepository.remove(tgChatId);
        if (result == 0) {
            throw new NotFoundChatException();
        }
    }
}

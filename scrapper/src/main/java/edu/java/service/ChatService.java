package edu.java.service;

import edu.java.exception.ExistChatException;
import edu.java.exception.NotFoundChatException;

public interface ChatService {
    void register(Long tgChatId) throws ExistChatException;

    void unregister(Long tgChatId) throws NotFoundChatException;
}

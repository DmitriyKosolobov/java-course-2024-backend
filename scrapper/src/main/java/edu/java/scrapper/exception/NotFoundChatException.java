package edu.java.scrapper.exception;

public class NotFoundChatException extends Exception {
    public NotFoundChatException() {
        super("Chat was not found");
    }
}


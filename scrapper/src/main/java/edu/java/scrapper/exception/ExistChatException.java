package edu.java.scrapper.exception;

public class ExistChatException extends Exception {
    public ExistChatException() {
        super("Chat already exists");
    }
}


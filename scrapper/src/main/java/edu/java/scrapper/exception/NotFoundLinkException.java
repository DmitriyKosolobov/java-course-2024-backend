package edu.java.scrapper.exception;

public class NotFoundLinkException extends Exception {
    public NotFoundLinkException() {
        super("Chat was not found");
    }
}

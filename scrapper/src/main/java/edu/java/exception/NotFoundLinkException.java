package edu.java.exception;

public class NotFoundLinkException extends Exception {
    public NotFoundLinkException() {
        super("Chat was not found");
    }
}

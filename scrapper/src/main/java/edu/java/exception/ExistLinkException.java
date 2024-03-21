package edu.java.exception;

public class ExistLinkException extends Exception {
    public ExistLinkException() {
        super("Link already exists");
    }
}

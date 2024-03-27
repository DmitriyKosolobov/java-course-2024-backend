package edu.java.scrapper.exception;

public class ExistLinkException extends Exception {
    public ExistLinkException() {
        super("Link already exists");
    }
}

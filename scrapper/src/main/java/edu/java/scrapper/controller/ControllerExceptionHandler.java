package edu.java.scrapper.controller;

import edu.java.scrapper.controller.dto.ErrorResponse;
import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.exception.RateLimitExceededException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<?> rateLimitExceededHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Превышено количество запросов", HttpStatus.TOO_MANY_REQUESTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidLinkHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Неверный формат url", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistChatException.class)
    public ResponseEntity<?> existChatHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Чат уже зарегистрирован", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistLinkException.class)
    public ResponseEntity<?> existLinkHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Ссылка уже добавлена", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundChatException.class)
    public ResponseEntity<?> notFoundChatHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Такого чата не существует", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundLinkException.class)
    public ResponseEntity<?> notFoundLinkHandler(Exception ex) {
        var errorResponse = createErrorResponse(ex, "Такой ссылки не существует", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createErrorResponse(Exception ex, String message, HttpStatus httpStatus) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace()).map(Objects::toString).toList();
        return new ErrorResponse(
            String.valueOf(httpStatus.value()),
            message,
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            stacktrace
        );
    }
}

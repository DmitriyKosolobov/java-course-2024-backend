package edu.java.controller;

import edu.java.controller.dto.ErrorResponse;
import edu.java.exception.ExistChatException;
import edu.java.exception.ExistLinkException;
import edu.java.exception.NotFoundChatException;
import edu.java.exception.NotFoundLinkException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handler(Exception ex) {
        var stacktrace = Arrays.stream(ex.getStackTrace()).map(Objects::toString).toList();
        var errorResponse = new ErrorResponse(
            "400",
            "Неверный формат url",
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            null
        );
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

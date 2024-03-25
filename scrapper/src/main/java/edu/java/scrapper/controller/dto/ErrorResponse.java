package edu.java.scrapper.controller.dto;

import java.util.List;

public record ErrorResponse(
    String code,
    String description,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}

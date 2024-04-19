package edu.java.scrapper.controller.dto;

import java.util.List;

public record UpdateRequest(
    Long id,
    String url,
    String description,
    List<Long> tgChatIds
) {
}

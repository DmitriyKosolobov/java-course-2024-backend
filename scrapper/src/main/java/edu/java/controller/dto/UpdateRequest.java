package edu.java.controller.dto;

import java.util.List;

public record UpdateRequest(
    Integer id,
    String url,
    String description,
    List<Integer> tgChatIds
) {
}

package edu.java.scrapper.controller.dto;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    Integer size
) {
}

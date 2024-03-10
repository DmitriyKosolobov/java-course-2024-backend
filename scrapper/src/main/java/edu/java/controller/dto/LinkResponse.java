package edu.java.controller.dto;

import java.net.URI;

public record LinkResponse(
    Integer id,
    URI url
) {
}

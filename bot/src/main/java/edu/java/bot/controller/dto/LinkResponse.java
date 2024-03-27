package edu.java.bot.controller.dto;

import java.net.URI;

public record LinkResponse(
    Long id,
    URI url
) {
}

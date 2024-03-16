package edu.java.bot.controller.dto;

import java.net.URI;

public record LinkResponse(
    Integer id,
    URI url
) {
}

package edu.java.scrapper.controller.dto;

import jakarta.validation.constraints.Pattern;

@SuppressWarnings("LineLength")
public record AddLinkRequest(
    @Pattern(regexp = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)")
    String link
) {
}

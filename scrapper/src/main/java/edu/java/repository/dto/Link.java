package edu.java.repository.dto;

import java.time.OffsetDateTime;

public record Link(
    Long id,
    String url,
    OffsetDateTime lastCheckTime
) {
}

package edu.java.scrapper.domain.jdbc.dto;

import java.time.OffsetDateTime;

public record Link(
    Long id,
    String url,
    OffsetDateTime lastCheckTime,
    Long answersCount,
    Long commitsCount
) {
}

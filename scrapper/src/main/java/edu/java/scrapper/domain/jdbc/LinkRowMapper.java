package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.dto.Link;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@SuppressWarnings("MagicNumber")
@Component
public class LinkRowMapper implements RowMapper<Link> {
    private static OffsetDateTime parseDate(String date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss.")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, false)
            .appendPattern("x")
            .toFormatter();

        return OffsetDateTime.parse(date, formatter);
    }

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Link(
            rs.getLong("id"),
            rs.getString("url"),
            parseDate(rs.getString("last_check_time")),
            rs.getLong("answers_count"),
            rs.getLong("commits_count")
        );
    }
}

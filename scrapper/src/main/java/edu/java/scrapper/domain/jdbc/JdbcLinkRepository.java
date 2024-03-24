package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.jdbc.dto.Link;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
@Repository
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static OffsetDateTime parseDate(String date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss.")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, false)
            .appendPattern("x")
            .toFormatter();

        return OffsetDateTime.parse(date, formatter);
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM Links";
        return jdbcTemplate.query(sql, (row, item) -> new Link(
            row.getLong("id"),
            row.getString("url"),
            parseDate(row.getString("last_check_time")),
            row.getLong("answers_count"),
            row.getLong("commits_count")
        ));
    }

    public List<Link> findAllByChatId(Long chatId) {
        String sql = """
            SELECT Links.*
            FROM ChatsLinks
            JOIN Links ON Links.id = ChatsLinks.link_id
            WHERE ChatsLinks.chat_id = ?""";
        return jdbcTemplate.query(sql, (row, item) -> new Link(
            row.getLong("id"),
            row.getString("url"),
            parseDate(row.getString("last_check_time")),
            row.getLong("answers_count"),
            row.getLong("commits_count")
        ), chatId);
    }

    private Long getLinkIdByUrl(String url) {
        String sql = "SELECT id FROM Links WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, url);
        } catch (Exception e) {
            return null;
        }
    }

    private Link getLinkByUrl(String url) {
        String sql = "SELECT * FROM Links WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (row, item) -> new Link(
                row.getLong("id"),
                row.getString("url"),
                parseDate(row.getString("last_check_time")),
                row.getLong("answers_count"),
                row.getLong("commits_count")
            ), url);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Link add(Long chatId, String url) {
        Long linkId = getLinkIdByUrl(url);
        if (linkId == null) {
            SimpleJdbcInsert insertIntoLink = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Links")
                .usingGeneratedKeyColumns("id")
                .usingColumns("url");
            linkId = insertIntoLink.executeAndReturnKey(Map.of("url", url)).longValue();
        }
        String sql = "INSERT INTO ChatsLinks (chat_id, link_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatId, linkId);

        return getLinkByUrl(url);
    }

    @Transactional
    public Link remove(Long chatId, String url) {

        Long linkId = getLinkIdByUrl(url);
        if (linkId == null) {
            return null;
        }

        Link link = getLinkByUrl(url);

        String sql = "DELETE FROM ChatsLinks WHERE chat_id = ? AND link_id = ?";
        int res = jdbcTemplate.update(sql, chatId, linkId);
        if (res == 0) {
            return null;
        }

        sql = "SELECT id FROM ChatsLinks WHERE link_id = ? LIMIT 1";
        List<Long> ids = jdbcTemplate.query(sql, (row, item) -> row.getLong("id"), linkId);

        if (ids.isEmpty()) {
            jdbcTemplate.update("DELETE FROM Links WHERE id = ? ", linkId);
        }
        return link;
    }

    public List<Link> findOldCheckedLinks() {
        String sql = """
            SELECT * FROM Links
            WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) / 60 > 10""";
        return jdbcTemplate.query(sql, (row, item) -> new Link(
            row.getLong("id"),
            row.getString("url"),
            parseDate(row.getString("last_check_time")),
            row.getLong("answers_count"),
            row.getLong("commits_count")
        ));
    }

    @Transactional
    public int updateLastCheckTime(Long linkId) {
        String sql = "UPDATE Links SET last_check_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, linkId);
    }

    public List<Long> findAllTgChatIdByLinkId(Long linkId) {
        String sql = """
            SELECT tg_chat_id
            FROM ChatsLinks
            JOIN Links ON Links.id = ChatsLinks.link_id
            JOIN Chats ON Chats.id = ChatsLinks.chat_id
            WHERE Links.id = ?""";
        return jdbcTemplate.query(sql, (row, item) -> row.getLong("tg_chat_id"), linkId);
    }
}

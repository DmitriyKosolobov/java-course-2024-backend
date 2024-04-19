package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.dto.Link;
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
    private final LinkRowMapper linkRowMapper;

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate, LinkRowMapper linkRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.linkRowMapper = linkRowMapper;
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM links";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

    public List<Link> findAllByChatId(Long chatId) {
        String sql = """
            SELECT links.*
            FROM chatslinks
            JOIN links ON links.id = chatslinks.link_id
            WHERE chatslinks.chat_id = ?""";
        return jdbcTemplate.query(sql, linkRowMapper, chatId);
    }

    private Long getLinkIdByUrl(String url) {
        String sql = "SELECT id FROM links WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, url);
        } catch (Exception e) {
            return null;
        }
    }

    private Link getLinkByUrl(String url) {
        String sql = "SELECT * FROM links WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, linkRowMapper, url);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Link add(Long chatId, String url) {
        Long linkId = getLinkIdByUrl(url);
        if (linkId == null) {
            SimpleJdbcInsert insertIntoLink = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("links")
                .usingGeneratedKeyColumns("id")
                .usingColumns("url");
            linkId = insertIntoLink.executeAndReturnKey(Map.of("url", url)).longValue();
        }
        String sql = "INSERT INTO chatslinks (chat_id, link_id) VALUES (?, ?)";
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

        String sql = "DELETE FROM chatslinks WHERE chat_id = ? AND link_id = ?";
        int res = jdbcTemplate.update(sql, chatId, linkId);
        if (res == 0) {
            return null;
        }

        sql = "SELECT id FROM chatslinks WHERE link_id = ? LIMIT 1";
        List<Long> ids = jdbcTemplate.query(sql, (row, item) -> row.getLong("id"), linkId);

        if (ids.isEmpty()) {
            jdbcTemplate.update("DELETE FROM links WHERE id = ? ", linkId);
        }
        return link;
    }

    public List<Link> findOldCheckedLinks(Long forceCheckDelay) {
        String sql = """
            SELECT * FROM links
            WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) > ?""";
        return jdbcTemplate.query(sql, linkRowMapper, forceCheckDelay);
    }

    @Transactional
    public int updateLastCheckTime(Long linkId) {
        String sql = "UPDATE links SET last_check_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, linkId);
    }

    public List<Long> findAllTgChatIdByLinkId(Long linkId) {
        String sql = """
            SELECT tg_chat_id
            FROM chatslinks
            JOIN chats ON chats.id = chatslinks.chat_id
            WHERE chatslinks.link_id = ?""";
        return jdbcTemplate.query(sql, (row, item) -> row.getLong("tg_chat_id"), linkId);
    }
}

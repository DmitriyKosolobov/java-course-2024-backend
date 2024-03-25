package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.dto.Chat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("MultipleStringLiterals")
@Repository
public class JdbcChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Chat> findAll() {
        String sql = "SELECT id, tg_chat_id FROM chats";
        return jdbcTemplate.query(sql, (row, item) ->
            new Chat(row.getLong("id"), row.getLong("tg_chat_id")));
    }

    public Long getChatIdByTgChatId(Long tgChatId) {
        String sql = "SELECT id FROM chats WHERE tg_chat_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, tgChatId);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Chat add(Long tgChatId) {
        String sql = "INSERT INTO chats VALUES (default, ?)";
        jdbcTemplate.update(sql, tgChatId);
        Long generatedId = getChatIdByTgChatId(tgChatId);
        return new Chat(generatedId, tgChatId);
    }

    @Transactional
    public int remove(Long tgChatId) {
        String sql = "DELETE FROM chats WHERE tg_chat_id = ?";
        return jdbcTemplate.update(sql, tgChatId);
    }
}

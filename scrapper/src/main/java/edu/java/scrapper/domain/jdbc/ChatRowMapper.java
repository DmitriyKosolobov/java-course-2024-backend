package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.dto.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ChatRowMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Chat(
            rs.getLong("id"),
            rs.getLong("tg_chat_id")
        );
    }
}

package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.dto.Chat;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.Tables.CHATS;

@Repository
public class JooqChatRepository {

    private final DSLContext dslContext;

    public JooqChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Chat> findAll() {
        return dslContext.selectFrom(CHATS)
            .fetch()
            .into(Chat.class);
    }

    public Long getChatIdByTgChatId(Long tgChatId) {
        return dslContext.select(CHATS.ID)
            .from(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .fetchOneInto(Long.class);
    }

    @Transactional
    public Chat add(Long tgChatId) {
        return dslContext.insertInto(CHATS, CHATS.TG_CHAT_ID)
            .values(tgChatId)
            .returning()
            .fetchOneInto(Chat.class);
    }

    @Transactional
    public int remove(Long tgChatId) {
        return dslContext.deleteFrom(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .execute();
    }

}

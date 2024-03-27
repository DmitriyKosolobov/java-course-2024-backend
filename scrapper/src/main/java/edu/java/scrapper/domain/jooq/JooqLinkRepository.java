package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.Tables.CHATS;
import static edu.java.scrapper.domain.jooq.Tables.CHATSLINKS;
import static edu.java.scrapper.domain.jooq.Tables.LINKS;

@Repository
public class JooqLinkRepository {

    private final DSLContext dslContext;

    private final LinkRecordMapper linkRecordMapper;

    public JooqLinkRepository(DSLContext dslContext, LinkRecordMapper linkRecordMapper) {
        this.dslContext = dslContext;
        this.linkRecordMapper = linkRecordMapper;
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINKS)
            .fetchInto(Link.class);
    }

    public List<Link> findAllByChatId(Long chatId) {
        return dslContext.select(LINKS.asterisk())
            .from(LINKS)
            .join(CHATSLINKS).on(CHATSLINKS.LINK_ID.eq(LINKS.ID))
            .where(CHATSLINKS.CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    private Long getLinkIdByUrl(String url) {
        return dslContext.select(LINKS.ID)
            .from(LINKS)
            .where(LINKS.URL.eq(url))
            .fetchOneInto(Long.class);
    }

    private Link getLinkByUrl(String url) {
        return dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url))
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Link add(Long chatId, String url) {
        Long linKId = getLinkIdByUrl(url);
        if (linKId == null) {
            linKId = dslContext.insertInto(LINKS, LINKS.URL)
                .values(url)
                .returning(LINKS.ID)
                .fetchOne()
                .getId();
        }
        return dslContext.insertInto(CHATSLINKS, CHATSLINKS.CHAT_ID, CHATSLINKS.LINK_ID)
            .values(chatId, linKId)
            .returning()
            .fetch()
            .map(linkRecordMapper::map)
            .getFirst();
    }

    @Transactional
    public Link remove(Long chatId, String url) {
        Long linkId = getLinkIdByUrl(url);
        if (linkId == null) {
            return null;
        }

        Link link = getLinkByUrl(url);
        boolean res = dslContext.deleteFrom(CHATSLINKS)
            .where(CHATSLINKS.CHAT_ID.eq(chatId))
            .and(CHATSLINKS.LINK_ID.eq(linkId))
            .execute() == 0;
        if (res) {
            return null;
        }

        List<Long> ids = dslContext.select(CHATSLINKS.ID)
            .from(CHATSLINKS)
            .where(CHATSLINKS.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
        if (ids.isEmpty()) {
            dslContext.deleteFrom(LINKS)
                .where(LINKS.ID.eq(linkId))
                .execute();
        }
        return link;
    }

    public List<Link> findOldCheckedLinks(Long forceCheckDelay) {
        return dslContext.selectFrom(LINKS)
            .where(LINKS.LAST_CHECK_TIME.lt(OffsetDateTime.now().minusSeconds(forceCheckDelay)))
            .fetchInto(Link.class);
    }

    @Transactional
    public int updateLastCheckTime(Long linkId) {
        return dslContext.update(LINKS)
            .set(LINKS.LAST_CHECK_TIME, OffsetDateTime.now())
            .where(LINKS.ID.eq(linkId))
            .execute();
    }

    public List<Long> findAllTgChatIdByLinkId(Long linkId) {
        return dslContext.select(CHATS.TG_CHAT_ID)
            .from(CHATSLINKS)
            .join(CHATS).on(CHATS.ID.eq(CHATSLINKS.CHAT_ID))
            .where(CHATSLINKS.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
    }
}

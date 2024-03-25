package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.dto.Link;
import edu.java.scrapper.domain.jooq.tables.records.ChatslinksRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import static edu.java.scrapper.domain.jooq.Tables.CHATSLINKS;
import static edu.java.scrapper.domain.jooq.Tables.LINKS;

@RequiredArgsConstructor
@Component
public class LinkRecordMapper implements RecordMapper<LinksRecord, Link> {

    private final DSLContext dslContext;

    @Override
    public Link map(LinksRecord r) {
        return new Link(
            r.get(LINKS.ID),
            r.get(LINKS.URL),
            r.get(LINKS.LAST_CHECK_TIME),
            r.get(LINKS.ANSWERS_COUNT),
            r.get(LINKS.COMMITS_COUNT)
        );
    }

    public Link map(ChatslinksRecord r) {
        Long linkId = r.get(CHATSLINKS.LINK_ID);
        return dslContext.selectFrom(LINKS)
            .where(LINKS.ID.eq(linkId))
            .fetch()
            .map(this)
            .getFirst();
    }
}

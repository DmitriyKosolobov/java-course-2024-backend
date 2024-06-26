/*
 * This file is generated by jOOQ.
 */

package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.jooq.tables.Chatslinks;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import edu.java.scrapper.domain.jooq.tables.records.ChatslinksRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import javax.annotation.processing.Generated;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatsRecord> CONSTRAINT_3 =
        Internal.createUniqueKey(Chats.CHATS, DSL.name("CONSTRAINT_3"), new TableField[] {Chats.CHATS.ID}, true);
    public static final UniqueKey<ChatsRecord> CONSTRAINT_3D = Internal.createUniqueKey(
        Chats.CHATS,
        DSL.name("CONSTRAINT_3D"),
        new TableField[] {Chats.CHATS.TG_CHAT_ID},
        true
    );
    public static final UniqueKey<ChatslinksRecord> CONSTRAINT_F = Internal.createUniqueKey(
        Chatslinks.CHATSLINKS,
        DSL.name("CONSTRAINT_F"),
        new TableField[] {Chatslinks.CHATSLINKS.ID},
        true
    );
    public static final UniqueKey<ChatslinksRecord> CONSTRAINT_F023 = Internal.createUniqueKey(
        Chatslinks.CHATSLINKS,
        DSL.name("CONSTRAINT_F023"),
        new TableField[] {Chatslinks.CHATSLINKS.CHAT_ID, Chatslinks.CHATSLINKS.LINK_ID},
        true
    );
    public static final UniqueKey<LinksRecord> CONSTRAINT_4 =
        Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_4"), new TableField[] {Links.LINKS.ID}, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_45 =
        Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_45"), new TableField[] {Links.LINKS.URL}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChatslinksRecord, ChatsRecord> CONSTRAINT_F0 =
        Internal.createForeignKey(
            Chatslinks.CHATSLINKS,
            DSL.name("CONSTRAINT_F0"),
            new TableField[] {Chatslinks.CHATSLINKS.CHAT_ID},
            Keys.CONSTRAINT_3,
            new TableField[] {Chats.CHATS.ID},
            true
        );
    public static final ForeignKey<ChatslinksRecord, LinksRecord> CONSTRAINT_F02 =
        Internal.createForeignKey(
            Chatslinks.CHATSLINKS,
            DSL.name("CONSTRAINT_F02"),
            new TableField[] {Chatslinks.CHATSLINKS.LINK_ID},
            Keys.CONSTRAINT_4,
            new TableField[] {Links.LINKS.ID},
            true
        );
}

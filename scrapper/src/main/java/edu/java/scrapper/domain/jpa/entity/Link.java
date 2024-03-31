package edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "links")
@Getter
@Setter
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "last_check_time", nullable = false)
    private OffsetDateTime lastCheckTime = OffsetDateTime.now();

    @Column(name = "answers_count", nullable = false)
    private Long answersCount = 0L;

    @Column(name = "commits_count", nullable = false)
    private Long commitsCount = 0L;

    @ManyToMany(mappedBy = "links")
    private List<Chat> chats = new ArrayList<>();


    public Link(String url, Chat chat) {
        this.url = url;
        this.chats.add(chat);
        chat.getLinks().add(this);
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
        chat.getLinks().add(this);
    }

    public void deleteChat(Chat chat) {
        this.chats.remove(chat);
        chat.getLinks().remove(this);
    }
}

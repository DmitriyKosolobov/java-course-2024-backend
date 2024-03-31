package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.domain.jpa.entity.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByTgChatId(Long tgChatId);

    void deleteByTgChatId(Long tgChatId);
}

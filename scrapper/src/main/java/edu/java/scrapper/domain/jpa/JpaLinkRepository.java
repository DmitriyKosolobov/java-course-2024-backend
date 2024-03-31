package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.domain.jpa.entity.Link;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    String FIND_OLD_CHECKED_LINKS = """
        SELECT * FROM links
        WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) > ?""";

    @Query(value = FIND_OLD_CHECKED_LINKS, nativeQuery = true)
    List<Link> findOldCheckedLinks(Long forceCheckDelay);

    Optional<Link> findByUrl(String url);
}

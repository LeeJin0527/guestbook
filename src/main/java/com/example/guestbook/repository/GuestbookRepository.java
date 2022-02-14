package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, SearchGuestbookRepository {
    @Query("select g, w from Guestbook g left  join g.writer w where g.gno =:gno")
    Object getGuestbookWithWriter(@Param("gno") Long gno);

    @Query("SELECT g, r From Guestbook g LEFT JOIN Reply r ON r.guestbook = g WHERE g.gno = :gno")
    List<Object[]> getGuestbookWithReply(@Param("gno") Long gno);

    @Query(value = "SELECT g, w, count(r)"+
    "FROM Guestbook g "+
    " LEFT JOIN g.writer w"+
    " LEFT JOIN Reply r ON r.guestbook = g " +
    "GROUP BY g",
            countQuery = "SELECT count(g) FROM Guestbook g")
    Page <Object[]> getGuestbookWithReplyCount(Pageable pageable);

    @Query("SELECT g, w, count(r)" +
    "FROM Guestbook g LEFT JOIN g.writer w "+
    "LEFT OUTER JOIN Reply r ON r.guestbook = g "+
            " WHERE g.gno = :gno")
    Object getGuestbookByGno(@Param("gno") Long gno);
}

package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Transactional
    @Query(value ="DELETE from Reply r WHERE r.guestbook.gno = :gno" )

    void deleteByGno(@Param("gno") Long gno);

    List<Reply> getRepliesByGuestbookOrderByRno(Guestbook guestbook);
}

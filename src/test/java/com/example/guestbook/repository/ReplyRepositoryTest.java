package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import com.example.guestbook.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;



    @Test
    public void insertReply(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long gno = (long)(Math.random()*100 )+1;
            Guestbook guestbook = Guestbook.builder().gno(gno).build();
            Reply reply = Reply.builder()
                    .text("Reply..." + i )
                    .guestbook(guestbook)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);

        });
    }

    @Test
    public void readReply1(){
        Optional<Reply> result = replyRepository.findById(1L);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getGuestbook());
    }

}
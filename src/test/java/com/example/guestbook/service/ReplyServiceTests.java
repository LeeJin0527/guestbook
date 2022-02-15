package com.example.guestbook.service;

import com.example.guestbook.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetList(){
        Long gno = 100L;

        List<ReplyDTO> replyDTOList = replyService.getList(gno);

        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));

    }
}

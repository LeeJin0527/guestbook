package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GuestbookServiceTest {

    @Autowired
    private GuestbookService service;



    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")  //현재 데이터베이스에 존재하는 회원 이메일
                .build();

        Long gno = service.register(guestbookDTO);

    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<GuestbookDTO, Object[]>  result = service.getList(pageRequestDTO);
//        System.out.println("PREV:" + resultDTO.isPrev());
//        System.out.println("NEXT:" + resultDTO.isNext());
//        System.out.println("TOTAL:" + resultDTO.getTotalPage());

        for (GuestbookDTO guestbookDTO:result.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("---------");
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testGet(){
        Long gno = 100L;
        GuestbookDTO guestbookDTO = service.get(gno);
        System.out.println(guestbookDTO);
    }

    @Test
    public void testRemove(){
        Long gno = 411L;
        service.removeWithReplies(gno);
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();
//        PageResultDTO<GuestbookDTO, Guestbook>  resultDTO = service.getList(pageRequestDTO);
//        System.out.println("PREV:" + resultDTO.isPrev());
//        System.out.println("NEXT:" + resultDTO.isNext());
//        System.out.println("TOTAL:" + resultDTO.getTotalPage());
//
//        for (GuestbookDTO guestbookDTO:resultDTO.getDtoList()){
//            System.out.println(guestbookDTO);
//        }
//        System.out.println("---------");
//        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }
}
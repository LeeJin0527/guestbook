package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import com.example.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;


    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach( i-> {

            Member member = Member.builder()
                    .email("user" + i + "aaa.com")
                    .build();

                Guestbook guestbook = Guestbook.builder()
                        .title("Title..."+ i)
                        .content("Content..." + i)
                        .writer(member)
                        .build();
              guestbookRepository.save(guestbook);
        });
    }

    @Transactional
    @Test
    public void testRead1(){
        Optional<Guestbook> result = guestbookRepository.findById(100L);
        Guestbook guestbook  = result.get();
        System.out.println(guestbook);
        System.out.println(guestbook.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = guestbookRepository.getGuestbookWithWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println('-');
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetGuestbookWithReply(){
        List<Object[]> result = guestbookRepository.getGuestbookWithReply(30L);
        for(Object[] arr :result){
            System.out.println(Arrays.toString(arr));
        }
    }
    
    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        
        Page<Object[]> result = guestbookRepository.getGuestbookWithReplyCount(pageable);
        result.get().forEach(row ->{
            Object [] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = guestbookRepository.getBoardByGno(30L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
//    @Test
//    public void updateTest(){
//        Optional<Guestbook> result = guestbookRepository.findById(300L);
//        if (result.isPresent()){
//            Guestbook guestbook = result.get();
//            guestbook.changeTitle("Change Title...");
//            guestbook.changeContent("Change Content...");
//
//            guestbookRepository.save(guestbook);
//
//        }
//    }
    //'title'로 검색
//    @Test
//    public void testQuery1(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        String keyword = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qGuestbook.title.contains(keyword);
//        builder.and(expression);
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//    }
    // content로 검색
//    @Test
//    public void testQuery2(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        String keyword = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qGuestbook.content.contains(keyword);
//        builder.and(expression);
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//    }
//    // writer로 검색
//    @Test
//    public void testQuery3(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        String keyword = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qGuestbook.writer.contains(keyword);
//        builder.and(expression);
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//    }

    // 다중 항목 테스트
//    @Test
//    public void testQuery4(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//
//        String keyword = "1";
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
//        BooleanExpression exContent = qGuestbook.content.contains(keyword);
//        BooleanExpression exAll = exTitle.or(exContent);
//        builder.and(exAll);
//        builder.and(qGuestbook.gno.gt(0L));
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//    }


}
package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;
import com.example.guestbook.repository.ReplyRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor

public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(GuestbookDTO dto){
        log.info("DTO-------------------");
        log.info(dto);
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGno();

    }

    @Override
    public GuestbookDTO get(Long gno){
        Object result = repository.getGuestbookByGno(gno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Guestbook) arr[0], (Member) arr[1], (Long)arr[2]);
    }

    @Override
    public PageResultDTO<GuestbookDTO, Object[]> getList(PageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
//
//        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Function<Object[], GuestbookDTO> fn = (en ->entityToDTO((Guestbook)en[0], (Member)en[1], (Long)en[2]));
        Page<Object[]> result = repository.getGuestbookWithReplyCount(requestDTO.getPageable(Sort.by("gno").descending()));


        return new PageResultDTO<>(result, fn);
    }


//    @Override
//    public GuestbookDTO read(Long gno) {
//        Optional<Guestbook> result = repository.findById(gno);
//        return result.isPresent()? entityToDTO(result.get()): null;
//
//    }

//    @Override
//    public void remove(Long gno) {
//        repository.deleteById(gno);
//    }




    @Transactional
    @Override
    public void removeWithReplies(Long gno){
        replyRepository.deleteByGno(gno);
        repository.deleteById(gno);
    }

    @Transactional
    @Override
    public void modify(GuestbookDTO dto) {

        //업데이트 하는 항목은 '제목', '내용'

//        Optional<Guestbook> result = repository.findById(dto.getGno());

        Guestbook guestbook = repository.getOne(dto.getGno());
        if(guestbook != null){

            System.out.println(guestbook);
//            Guestbook entity = result.get();
            System.out.println(dto.getTitle().getClass().getName());
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());

            repository.save(guestbook);

        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }

        if (type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }

//        if (type.contains("w")){
//            conditionBuilder.or(qGuestbook.writer.contains(keyword));
//        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}

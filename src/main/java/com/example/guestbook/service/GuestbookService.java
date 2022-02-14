package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;

public interface GuestbookService {

//    GuestbookDTO read(Long gno);
    Long register(GuestbookDTO dto);
    PageResultDTO<GuestbookDTO, Object[]> getList(PageRequestDTO requestDTO);
    GuestbookDTO get(Long gno);
//    void remove(Long gno);
    void removeWithReplies(Long gno);

    void modify(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Member member  = Member.builder().email(dto.getWriterEmail()).build();
        Guestbook guestbook  = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return guestbook;
    }

    default GuestbookDTO entityToDTO(Guestbook entity, Member member, Long replyCount){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
//                .writer(entity.getWriter())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .replyCount(replyCount.intValue())
                .build();
        return guestbookDTO;
    }
}

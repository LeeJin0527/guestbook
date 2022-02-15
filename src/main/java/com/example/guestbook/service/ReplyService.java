package com.example.guestbook.service;

import com.example.guestbook.dto.ReplyDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); //댓글의 등록

    List<ReplyDTO> getList(Long gno); //특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO); //댓글 수정

    void remove(Long rno); //댓글 삭제

    default Reply dtoToEntity(ReplyDTO replyDTO){
        Guestbook guestbook = Guestbook.builder().gno(replyDTO.getGno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .guestbook(guestbook)
                .build();
        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }
}

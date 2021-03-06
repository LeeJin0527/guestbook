package com.example.guestbook.service;

import com.example.guestbook.dto.ReplyDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Reply;
import com.example.guestbook.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO){
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }
    @Override
    public List<ReplyDTO> getList(Long gno){
        List<Reply> result = replyRepository.getRepliesByGuestbookOrderByRno(Guestbook.builder().gno(gno).build());

        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList()) ;
    }

    @Override
    public void modify(ReplyDTO replyDTO){
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno){
        replyRepository.deleteById(rno);
    }
}

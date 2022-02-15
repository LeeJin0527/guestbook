package com.example.guestbook.dto;

import com.example.guestbook.entity.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long gno; //게시글 번호

    private LocalDateTime regDate, modDate;
}

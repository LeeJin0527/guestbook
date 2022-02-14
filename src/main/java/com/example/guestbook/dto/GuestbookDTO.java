package com.example.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestbookDTO {

    private Long gno;
    private String title;
    private String content;
    private String writerEmail; //작성자의 이메일 (id)
    private String writerName; // 작성자의 이름
    private LocalDateTime regDate, modDate;
    private int replyCount; // 해당 게시물의 댓글 수
}

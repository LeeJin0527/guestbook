package com.example.guestbook.controller;

import com.example.guestbook.dto.ReplyDTO;
import com.example.guestbook.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"2. 댓글"})
@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService; // 자동 주입을 위한 final

    @ApiOperation(value = "댓글  목록을 반환하는 메소드 ")
    @GetMapping(value = "/guestbook/{gno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByGuestbook(@PathVariable ("gno")Long gno){
        return new ResponseEntity<>(replyService.getList(gno), HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 등록 후 목록을 반환하는 메소드 ")
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 삭제 결과를  반환하는 메소드 ")
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO:" + rno );

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @ApiOperation(value = "댓글 수정 결과를  반환하는 메소드 ")
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {

        log.info("~~~~" +replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

}

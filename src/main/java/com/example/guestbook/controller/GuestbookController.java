package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Api(tags = {"1.게시물"})
@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

    @ApiOperation(value = "게시글 목록을 반환하는 메소드 ")
    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @ApiOperation(value = "게시글 목록을 반환하는 메소드")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    @ApiOperation(value = "글 등록하는 메소드")
    @GetMapping("/register")
    public void register(){
        log.info("register get..");
    }

    @ApiOperation(value = "글 등록후에 이동하는  메소드")
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @ApiOperation(value = "글 조회 , 수정페이지 ")
    @GetMapping({"/read", "/modify"})
    public void read( @ModelAttribute("requestDTO") PageRequestDTO requestDTO, long gno, Model model ){

        log.info("gno: " + gno);

        GuestbookDTO dto = service.get(gno);

        model.addAttribute("dto", dto);

    }

    @ApiOperation(value = "게시글 삭제 결과 반환하는 메소드")
    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno: " + gno);
        service.removeWithReplies(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @ApiOperation(value = "게시글 수정 결과를  반환하는 메소드")
    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno",dto.getGno());


        return "redirect:/guestbook/read";

    }
}

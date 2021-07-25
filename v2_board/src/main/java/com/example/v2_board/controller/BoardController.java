package com.example.v2_board.controller;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/hello")
    public String hello(){
        return "board/hello";
    }

    @GetMapping("/list")
    public String boardList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        log.info("-- board list --");
        List<BoardDTO> boardList = boardService.getAll();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/form")
    public String boardForm(){
        log.info("-- board form --");
        return "board/form";
    }

}

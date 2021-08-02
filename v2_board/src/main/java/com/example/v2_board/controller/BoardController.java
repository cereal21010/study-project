package com.example.v2_board.controller;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.utills.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String boardList(Model model
                            , @RequestParam(defaultValue = "1") String pageNum
                            , @RequestParam(defaultValue = "10") String contentNum ) throws Exception {
        log.info("-- board list --");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("pageNum", pageNum);
        paramMap.put("contentNum", contentNum);
        List<BoardDTO> boardList = boardService.selectList(paramMap);
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/form")
    public String boardForm(){
        log.info("-- board form --");
        return "board/form";
    }

    @GetMapping("/content/{seq}")
    public String boardContent(Model model, @PathVariable("seq") int seq) throws Exception {
        log.info("-- board content --");
        BoardDTO board =  boardService.getOne(seq);
        model.addAttribute("board", board);
        return "board/content";
    }

    @GetMapping("/edit/{seq}")
    public String boardEdit(Model model, @PathVariable("seq") int seq) throws Exception{
        //작성자 본인인지 확인하는 로직 추가해야뎅..
        log.info("-- board edit --");
        BoardDTO board = boardService.getOne(seq);
        model.addAttribute("board", board);
        return "board/form";
    }

}

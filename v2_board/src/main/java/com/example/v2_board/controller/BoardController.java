package com.example.v2_board.controller;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.FileDTO;
import com.example.v2_board.dto.SearchDTO;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.service.FileService;
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
    private final FileService fileService;

    @GetMapping("/hello")
    public String hello(){
        return "board/hello";
    }

    @GetMapping("/list")
    public String boardList(Model model, SearchDTO searchDTO) throws Exception {
        //검색을 했을 때는 다시 1페이지로 ( 현재는 3페이지에서 검색했을 경우 3페이지 그대로 있다 )  
        log.info("-- board list --");

        PageMaker pm = boardService.getPageMaker(searchDTO);
        List<BoardDTO> boardList = boardService.selectList(searchDTO);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pm", pm);
        model.addAttribute("searchDTO", searchDTO);
        return "board/list";
    }

    @GetMapping("/form")
    public String boardForm(Model model, SearchDTO searchDTO){
        log.info("-- board form --");
        model.addAttribute("searchDTO", searchDTO);
        return "board/form";
    }

    @GetMapping("/content/{seq}")
    public String boardContent(Model model, @PathVariable("seq") int seq, SearchDTO searchDTO) throws Exception {
        log.info("-- board content --");
        BoardDTO board =  boardService.getOne(seq);
        List<FileDTO> files = fileService.selectList(board.getSeq());
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        model.addAttribute("searchDTO", searchDTO);
        return "board/content";
    }

    @GetMapping("/edit/{seq}")
    public String boardEdit(Model model, @PathVariable("seq") int seq, SearchDTO searchDTO) throws Exception{
        //작성자 본인인지 확인하는 로직 추가해야뎅..
        log.info("-- board edit --");
        BoardDTO board = boardService.getOne(seq);
        model.addAttribute("board", board);
        model.addAttribute("searchDTO", searchDTO);
        return "board/form";
    }

}

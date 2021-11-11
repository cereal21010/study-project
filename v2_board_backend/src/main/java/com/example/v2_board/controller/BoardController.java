package com.example.v2_board.controller;

import com.example.v2_board.vo.BoardVO;
import com.example.v2_board.vo.FileVO;
import com.example.v2_board.vo.SearchVO;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.service.FileService;
import com.example.v2_board.utills.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping("/hello")
    public String hello() {
        return "board/hello";
    }

    @GetMapping("/list")
    public String boardList(Model model, SearchVO searchVO) throws Exception {
        //검색을 했을 때는 다시 1페이지로 ( 현재는 3페이지에서 검색했을 경우 3페이지 그대로 있다 )  
        log.info("-- board list --");

        PageMaker pm = boardService.getPageMaker(searchVO);
        List<BoardVO> boardList = boardService.selectList(searchVO);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pm", pm);
        model.addAttribute("searchVO", searchVO);
        return "board/list";
    }

    @GetMapping("/form")
    public String boardForm(Model model, SearchVO searchVO) {
        log.info("-- board form --");
        model.addAttribute("searchVO", searchVO);
        return "board/form";
    }

    @GetMapping("/content/{seq}")
    public String boardContent(Model model, @PathVariable("seq") int seq, SearchVO searchVO) throws Exception {
        log.info("-- board content --");
        BoardVO board = boardService.getOne(seq);
        List<FileVO> files = fileService.selectList(board.getSeq());
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        model.addAttribute("searchVO", searchVO);
        return "board/content";
    }

    @GetMapping("/edit/{seq}")
    public String boardEdit(Model model, @PathVariable("seq") int seq, SearchVO searchVO) throws Exception {
        //작성자 본인인지 확인하는 로직 추가해야뎅..
        log.info("-- board edit --");
        BoardVO board = boardService.getOne(seq);
        List<FileVO> files = fileService.selectList(board.getSeq());
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        model.addAttribute("searchVO", searchVO);
        return "board/form";
    }


    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    public ResponseEntity<?> saveBoard(BoardVO vo, List<MultipartFile> files) throws Exception {
        log.info("-- api board insert --");

//        vo.setWriter("user01");   //testData

        try {
            boardService.insert(vo);

            if (files != null) {
                if (!files.get(0).isEmpty()) {
                    fileService.saveFile(files, vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package com.example.v2_board.api;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/board")
@Slf4j
public class BoardApi {

    private final BoardService boardService;

    @PostMapping("/save")
    @ResponseBody
    public String saveBoard(@ModelAttribute BoardDTO dto) throws Exception{
        log.info("-- api board save --");
        dto.setWriterSeq(1);
        dto.setWriter("testerLee");
        boardService.insert(dto);
        return "000";
    }

}

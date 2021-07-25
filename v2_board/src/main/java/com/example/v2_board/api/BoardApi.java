package com.example.v2_board.api;

import com.example.v2_board.dto.BoardDTO;
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

    @PostMapping("/save")
    @ResponseBody
    public String saveBoard(@ModelAttribute BoardDTO dto) throws Exception{
        log.info("-- api board save --");
        log.info(dto.getTitle());
        return "tt";
    }

}

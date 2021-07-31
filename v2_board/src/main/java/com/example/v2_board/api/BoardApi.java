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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/board")
@Slf4j
public class BoardApi {

    private final BoardService boardService;

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveBoard(BoardDTO dto) throws Exception{
        log.info("-- api board save --");
        Map<String, Object> map = new HashMap<>();
        dto.setWriterSeq(1);
        dto.setWriter("user01");
        boardService.insert(dto);
        map.put("result", "000");
        return map;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteBoard(@ModelAttribute BoardDTO dto) throws Exception {
        log.info("-- api board delete --");
        Map<String, Object> map = new HashMap<>();
        boardService.delete(dto.getSeq());
        map.put("result", "0000");
        return map;
    }

}

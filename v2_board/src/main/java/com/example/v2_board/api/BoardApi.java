package com.example.v2_board.api;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@Slf4j
public class BoardApi {

    private final BoardService boardService;

    @PostMapping("/register")
    public Map<String, Object> saveBoard(@RequestParam("file")MultipartFile files, BoardDTO dto) throws Exception{
        log.info("-- api board save --");
        Map<String, Object> map = new HashMap<>();
        dto.setWriterSeq(1);
        dto.setWriter("user01");
        boardService.insert(dto);
        map.put("result", "000");
        return map;
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteBoard(BoardDTO dto) throws Exception{
        log.info("-- api board delete --");
        Map<String, Object> map = new HashMap<>();
        boardService.delete(dto.getSeq());
        map.put("result", "000");
        return map;
    }

    @PostMapping("/update")
    public Map<String, Object> updateBoard(BoardDTO dto) throws Exception{
        log.info("-- api board update --");
        Map<String, Object> map = new HashMap<>();
        boardService.update(dto);
        map.put("result", "000");
        return map;
    }

}

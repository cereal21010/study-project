package com.example.v2_board_backend_t1.controller;

import com.example.v2_board_backend_t1.service.TestService;
import com.example.v2_board_backend_t1.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    final private TestService testService;

    @RequestMapping("/query")
    public List<BoardVO> query() throws Exception {

        return testService.getAll();
    }
}

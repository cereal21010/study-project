package com.example.v2_board_backend_t1.service;

import com.example.v2_board_backend_t1.mapper.TestMapper;
import com.example.v2_board_backend_t1.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    final private TestMapper testMapper;

    public List<BoardVO> getAll() throws Exception {
        return testMapper.getAll();
    }

}

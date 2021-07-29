package com.example.v2_board.service;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardDTO> getAll() throws Exception{
        return boardMapper.getAll();
    }

    public int insert(BoardDTO dto) throws Exception{
        return boardMapper.insert(dto);
    }

    public BoardDTO getOne(int seq) throws Exception{
        return boardMapper.getOne(seq);
    }

    public int delete(int seq) throws Exception{
        return boardMapper.delete(seq);
    }
}

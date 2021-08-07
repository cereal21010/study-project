package com.example.v2_board.mapper;



import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.SearchDTO;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    public List<BoardDTO> getAll();
    public int insert(BoardDTO dto);
    public BoardDTO getOne(int seq);
    public int delete(int seq);
    public int update(BoardDTO dto);
    public int getAllCount(SearchDTO dto);
    public List<BoardDTO> selectList(SearchDTO dto);
}

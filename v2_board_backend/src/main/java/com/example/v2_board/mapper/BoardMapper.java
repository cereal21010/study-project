package com.example.v2_board.mapper;


import com.example.v2_board.vo.BoardVO;
import com.example.v2_board.vo.SearchVO;

import java.util.List;

public interface BoardMapper {
    public List<BoardVO> getAll();

    public int insert(BoardVO vo);

    public BoardVO getOne(int seq);

    public int delete(int seq);

    public int update(BoardVO vo);

    public int getAllCount(SearchVO vo);

    public List<BoardVO> selectList(SearchVO vo);

    public void increaseViewCount(BoardVO vo);
}

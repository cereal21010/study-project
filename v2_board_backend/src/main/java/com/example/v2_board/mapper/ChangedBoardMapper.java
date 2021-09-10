package com.example.v2_board.mapper;

import com.example.v2_board.vo.ChangedBoardVO;

import java.util.List;

public interface ChangedBoardMapper {
    public List<ChangedBoardVO> getBoardList(int boardSeq);
    public void insertChangedBoard(ChangedBoardVO vo);
}

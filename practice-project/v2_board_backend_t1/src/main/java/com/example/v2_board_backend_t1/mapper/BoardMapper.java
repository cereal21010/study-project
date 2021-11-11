package com.example.v2_board_backend_t1.mapper;

import com.example.v2_board_backend_t1.service.BoardService;
import com.example.v2_board_backend_t1.vo.*;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    public List<BoardVO> getAll();
    public int insertBoard(BoardVO vo);
    public int updateBoard(BoardVO vo);
    public int deleteBoard(int seq);
    public BoardVO findBySeq(int seq);
    public List<BoardVO> getBoardList(SearchVO searchVO);
    public List<BoardFileVO> getBoardFiles(Map paramMap);
    public void increaseViewCount(int seq);
    public int deleteBoardFile(Map paramMap);
    public int insertBoardFile(BoardFileVO boardFileVO);
    public List<BoardCommentVO> getBoardComments(Map paramMap);
    public int insertBoardComment(BoardCommentVO boardCommentVO);
    public void deleteBoardComment(Map paramMap);
    public int insertLikeBoard(Map boardSeq);
    public void insertChangeBoard(ChangeBoardVO changeBoardVO);
}

package com.example.v2_board.mapper;

import com.example.v2_board.vo.CommentVO;

import java.util.List;

public interface CommentMapper {
    public List<CommentVO> getCommentList(int boardSeq);
    public void insertComment(CommentVO vo);
    public void deleteComment(int seq);
}

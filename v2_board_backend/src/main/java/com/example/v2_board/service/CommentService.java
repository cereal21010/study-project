package com.example.v2_board.service;

import com.example.v2_board.mapper.CommentMapper;
import com.example.v2_board.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public List<CommentVO> getCommentList(int boardSeq) {
        return commentMapper.getCommentList(boardSeq);
    }

    public void insertCommnet(CommentVO vo) {
        commentMapper.insertComment(vo);
    }

    public void deleteComment(int seq) {
        commentMapper.deleteComment(seq);
    }

    public void updateComment(CommentVO vo) {
        commentMapper.updateComment(vo);
    }
}

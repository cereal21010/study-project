package com.example.v2_board_backend_t1.vo.response;

import com.example.v2_board_backend_t1.vo.BoardVO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ResponseBoardVO {
    private int seq;
    private String title;
    private String content;
    private String writer;
    private boolean isPassword;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String category;
    private String viewCount;


    public ResponseBoardVO(BoardVO vo) {
        this.seq = vo.getSeq();
        this.title = vo.getTitle();
        this.content = vo.getContent();
        this.writer = vo.getWriter();
        this.isPassword = vo.getPassword() != null ? true : false;
        this.createdDate = vo.getCreatedDate();
        this.modifiedDate = vo.getModifiedDate();
        this.category = vo.getCategory();
        this.viewCount = vo.getViewCount();
    }
}

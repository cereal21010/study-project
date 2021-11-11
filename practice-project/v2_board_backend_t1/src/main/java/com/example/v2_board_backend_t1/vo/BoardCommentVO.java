package com.example.v2_board_backend_t1.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class BoardCommentVO {
    private int seq;
    private String writer;
    private String content;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private int boardSeq;
    private String password;
}

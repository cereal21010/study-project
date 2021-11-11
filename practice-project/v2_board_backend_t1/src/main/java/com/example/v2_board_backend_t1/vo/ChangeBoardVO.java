package com.example.v2_board_backend_t1.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@Builder
public class ChangeBoardVO {
    private int seq;
    private String title;
    private String content;
    private String writer;
    private String category;
    private Timestamp createdDate;
    private int originBoardSeq;


}

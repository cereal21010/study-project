package com.example.v2_board_backend_t1.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class BoardFileVO {
    private int seq;
    private String saveName;
    private String originalName;
    private long saveSize;
    private Timestamp createdDate;
    private int boardSeq;
}

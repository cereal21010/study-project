package com.example.v2_board.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FileVO {
    private int seq;
    private String originalName;
    private String saveName;
    private long saveSize;
    private String deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime deletedDate;
    private int boardSeq;
}

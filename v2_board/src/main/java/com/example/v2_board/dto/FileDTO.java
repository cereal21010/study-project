package com.example.v2_board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FileDTO {
    private int seq;
    private String originalName;
    private String saveName;
    private long saveSize;
    private String deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime deletedDate;
    private int boardSeq;
}

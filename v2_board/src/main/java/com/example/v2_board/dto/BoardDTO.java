package com.example.v2_board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter @Getter
public class BoardDTO {
    private int seq;
    private String title;
    private String content;
    private int writerSeq;
    private String writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String category;

    public String createdDateConvert(){
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String modifiedDateConvert(){
        return this.modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

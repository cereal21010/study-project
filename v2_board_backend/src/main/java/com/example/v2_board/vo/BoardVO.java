package com.example.v2_board.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class BoardVO {
    private int seq;
    private String title;
    private String contents;
    private String writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String category;
    private String password;
    private int viewCount;

    public String createdDateConvert() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String modifiedDateConvert() {
        return this.modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

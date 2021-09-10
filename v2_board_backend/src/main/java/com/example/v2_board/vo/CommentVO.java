package com.example.v2_board.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentVO {
    private int seq;
    private String writer;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int boardSeq;
}

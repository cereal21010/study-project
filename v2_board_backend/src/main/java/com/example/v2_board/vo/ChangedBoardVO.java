package com.example.v2_board.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChangedBoardVO {
    private int seq;
    private String title;
    private String contents;
    private String writer;
    private String category;
    private LocalDateTime changedDate;
    private int originBoardSeq;

    public ChangedBoardVO(BoardVO vo) {
        this.title = vo.getTitle();
        this.contents = vo.getContents();
        this.writer = vo.getWriter();
        this.category = vo.getCategory();
        this.originBoardSeq = vo.getSeq();
    }
}

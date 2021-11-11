package com.example.v2_board_backend_t1.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    private String writer;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String category;
    private String password;
    private String viewCount;

    private List<BoardFileVO> boardFiles = new ArrayList<>();
    private List<BoardCommentVO> boardComments = new ArrayList<>();

    public boolean getIsPassword() {
        return this.password == null ? false : true;
    }

    public void setFiles(List<BoardFileVO> boardFiles) {
        this.boardFiles.addAll(boardFiles);
    }

    public void setComments(List<BoardCommentVO> boardComments) {
        this.boardComments.addAll(boardComments);
    }


}

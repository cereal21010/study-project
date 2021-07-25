package com.example.v2_board.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
public class BoardDTO {
    private int seq;
    private String title;
    private String contents;
    private int writerSeq;
    private String writer;
    private Date createdDate;
    private Date modifiedDate;
    private String category;

    public String createdDateConvert(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.createdDate);
    }

    public String modifiedDateConvert(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.modifiedDate);
    }
}

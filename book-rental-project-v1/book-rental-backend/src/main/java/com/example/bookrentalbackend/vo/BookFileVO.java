package com.example.bookrentalbackend.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookFileVO {

    private long seq;
    private long bookSeq;
    private String originalName;
    private String saveName;
    private String filePath;
    private long saveSize;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

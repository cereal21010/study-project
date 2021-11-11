package com.example.v2_board_backend_t1.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchVO {
    private String title;
    private String content;
    private String writer;
    private String category;
    private String sort;
    private String order = "desc";
    private String startPage = "1";
    private String prePage = "10";
}

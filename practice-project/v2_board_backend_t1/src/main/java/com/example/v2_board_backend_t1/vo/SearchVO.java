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
    private int currentPage = 1;
    private int prePage = 10;

    public int getStartInPage() {
        return (this.currentPage - 1 < 0 ? 0 : this.currentPage - 1) * this.prePage;
    }

}

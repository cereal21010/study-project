package com.example.bookrental.vo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchVO {
    private String idNo;
    private String name;
    private String author;
    private String sort;
    private String order = "desc";
    private int currentPage = 1;
    private int prePage = 10;

    public int getStartInPage() {
        return (this.currentPage - 1 < 0 ? 0 : this.currentPage - 1) * this.prePage;
    }
}

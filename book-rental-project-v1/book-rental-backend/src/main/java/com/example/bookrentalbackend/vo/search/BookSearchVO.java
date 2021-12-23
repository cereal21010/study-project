package com.example.bookrentalbackend.vo.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchVO {
    private String idNo;
    private String name;
    private String sort;
    private String order = "desc";
}

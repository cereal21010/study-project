package com.example.v2_board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter @Setter
public class SearchDTO {
    int pageNum = 1;
    int contentNum = 10;
    String searchType;
    String keyword;
    String sort;
    String order;

}

package com.example.v2_board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter @Setter
public class SearchDTO {
    int pageNum = 1;
    //페이징 처리시 시작하는 게시글 지정할 때 사용
    int startContentNum = 0;
    int contentNum = 10;
    String searchType;
    String keyword;
    String sort;
    String order;

}

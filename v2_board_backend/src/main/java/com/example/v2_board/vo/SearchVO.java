package com.example.v2_board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchVO {
    int pageNum = 1;
    //페이징 처리시 시작하는 게시글 지정할 때 사용
    int startContentNum = 0;
    int endContentNum = 1;
    int contentNum = 10;
    String searchType;
    String keyword;
    String sort;
    String order = "desc";
    int totalCount = 0;
    int listLength = 0;



}

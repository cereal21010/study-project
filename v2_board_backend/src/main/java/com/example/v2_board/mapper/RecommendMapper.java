package com.example.v2_board.mapper;

import java.util.Map;

public interface RecommendMapper {
    public int getRecommendCount(int boardSeq);
    public int isRecommended(Map<String, Integer> paramMap);
    public void insertRecommend(Map<String, Integer> paramMap);
    public void deleteRecommend(Map<String, Integer> paramMap);
}

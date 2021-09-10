package com.example.v2_board.service;

import com.example.v2_board.mapper.RecommendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;

    public int getReCommendCount(int boardSeq) {
        return recommendMapper.getRecommendCount(boardSeq);
    }

    public Boolean isRecommended(int boardSeq, int memberSeq) {
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("boardSeq", boardSeq);
        paramMap.put("memberSeq", memberSeq);
        return recommendMapper.isRecommended(paramMap) > 0 ? true : false;
    }

    public void insertRecommend(Map<String, Integer> paramMap) {
        recommendMapper.insertRecommend(paramMap);
    }

    public void deleteRecommend(int boardSeq, int memberSeq) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("boardSeq", boardSeq);
        paramMap.put("memberSeq", memberSeq);
        recommendMapper.deleteRecommend(paramMap);
    }
}

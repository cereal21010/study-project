package com.example.v2_board.service;

import com.example.v2_board.mapper.RecommendMapper;
import com.example.v2_board.service.login.MemberService;
import com.example.v2_board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;
    private final MemberService memberService;

    public int getReCommendCount(int boardSeq) {
        return recommendMapper.getRecommendCount(boardSeq);
    }

    public Boolean isRecommended(int boardSeq, String loginId) {
        MemberVO memberVO = memberService.findById(loginId);
        if (memberVO == null) {
            return false;
        }
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("boardSeq", boardSeq);
        paramMap.put("memberSeq", memberVO.getSeq());
        return recommendMapper.isRecommended(paramMap) > 0 ? true : false;
    }

    public void insertRecommend(Map<String, String> argMap) {
        MemberVO memberVO = memberService.findById(argMap.get("loginId"));
        Map<String, Integer> paramMap = new HashMap<>();
        int boardSeq = Integer.parseInt(argMap.get("boardSeq"));
        paramMap.put("boardSeq", boardSeq);
        paramMap.put("memberSeq", memberVO.getSeq());
        recommendMapper.insertRecommend(paramMap);
    }

    public void deleteRecommend(Map<String, String> argMap) {
        MemberVO memberVO = memberService.findById(argMap.get("loginId"));
        Map<String, Integer> paramMap = new HashMap<>();
        int boardSeq = Integer.parseInt(argMap.get("boardSeq"));
        paramMap.put("boardSeq", boardSeq);
        paramMap.put("memberSeq", memberVO.getSeq());
        recommendMapper.deleteRecommend(paramMap);
    }
}

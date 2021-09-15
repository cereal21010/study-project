package com.example.v2_board.service;

import com.example.v2_board.mapper.MemberMapper;
import com.example.v2_board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSerivce {
    private final MemberMapper memberMapper;

    public MemberVO getOneMember(MemberVO vo) {
        return memberMapper.getOneMember(vo);
    }

    public void insertMember(MemberVO vo) {
        memberMapper.insertMember(vo);
    }
}

package com.example.v2_board.mapper;

import com.example.v2_board.vo.MemberVO;

public interface MemberMapper {
    public MemberVO getOneMember(MemberVO vo);
    public void insertMember(MemberVO vo);
}

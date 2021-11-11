package com.example.v2_board.mapper;

import com.example.v2_board.vo.MemberVO;

import java.util.List;

public interface MemberMapper {
    public MemberVO getOneMember(MemberVO vo);

    public void insertMember(MemberVO vo);

    public List<MemberVO> getAllMember();

    public MemberVO findBySeq(int seq);

    public MemberVO findById(String memberId);

    public void updateMember(MemberVO vo);
}

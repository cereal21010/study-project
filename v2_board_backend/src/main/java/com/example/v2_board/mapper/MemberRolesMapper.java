package com.example.v2_board.mapper;

import com.example.v2_board.vo.MemberRolesVO;

import java.util.List;

public interface MemberRolesMapper {
    public List<MemberRolesVO> findByMemberSeq(int memberSeq);

    public void insertMemberRoles(MemberRolesVO vo);
}

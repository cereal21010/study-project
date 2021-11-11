package com.example.v2_board.service.login;

import com.example.v2_board.Exception.ApiException;
import com.example.v2_board.common.enums.ExceptionEnum;
import com.example.v2_board.mapper.MemberMapper;
import com.example.v2_board.mapper.MemberRolesMapper;
import com.example.v2_board.vo.MemberRolesVO;
import com.example.v2_board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberRolesMapper memberRolesMapper;

    public MemberVO getOneMember(MemberVO vo) {
        return memberMapper.getOneMember(vo);
    }

    public void insertMember(MemberVO vo) throws ApiException {
        checkDuplicateEmail(vo.getMemberId());
        memberMapper.insertMember(vo);
    }

    public MemberVO findById(String memberId) {

        return memberMapper.findById(memberId);
    }

    public MemberVO findBySeq(int seq) {
        MemberVO memberVO = memberMapper.findBySeq(seq);
        return memberVO;
    }

    public void updateMember(MemberVO vo) {
        memberMapper.updateMember(vo);
    }

    public MemberVO loginMember(String id, String password) {

        MemberVO memberVO = memberMapper.findById(id);

        if (memberVO != null) {
            if (password.equals(memberVO.getMemberPassword())) {
                return memberVO;
            } else {
                throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH);
            }
        } else {
            throw new ApiException(ExceptionEnum.NOT_FIND_EMAIL);
        }
    }

    private void checkDuplicateEmail(String memberId) {
        MemberVO memberVO = memberMapper.findById(memberId);
        if (memberVO != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

    }
}

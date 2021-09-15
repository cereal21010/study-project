package com.example.v2_board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberVO {
    private int seq;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhoneNumber;
}

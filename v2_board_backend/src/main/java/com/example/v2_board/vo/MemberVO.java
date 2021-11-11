package com.example.v2_board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberVO {
    private int seq;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhoneNumber;
    private Date createdDate;
    private Date modifiedDate;

}

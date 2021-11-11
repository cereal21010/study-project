package com.example.v2_board.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    private int seq;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberPhoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}

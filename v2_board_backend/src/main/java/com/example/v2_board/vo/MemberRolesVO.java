package com.example.v2_board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberRolesVO {
    private int seq;
    private String role;
    private int memberSeq;
}

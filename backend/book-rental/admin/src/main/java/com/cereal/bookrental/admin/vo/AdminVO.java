package com.cereal.bookrental.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AdminVO {

    private long seq;
    private String id;
    private String password;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

package com.example.bookrentalbackend.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CustomerVO {

    private long seq;
    private String id;
    private String password;
    private String name;
    private int birthday;
    private int phoneNumber;
    private String memo;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

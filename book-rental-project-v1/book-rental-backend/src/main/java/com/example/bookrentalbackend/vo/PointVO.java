package com.example.bookrentalbackend.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PointVO {
    private long seq;
    private long rentalSeq;
    private long customerSeq;
    private String state;
    private int pointAmount;
    private Timestamp transactionDate;
    private int remainPoint;
    private int beforePoint;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

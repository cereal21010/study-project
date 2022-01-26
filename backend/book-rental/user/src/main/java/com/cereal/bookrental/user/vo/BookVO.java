package com.cereal.bookrental.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookVO {
    private long seq;
    private String idNo;
    private String name;
    private String author;
    private int price;
    private int rentalFee;
    private int rentalPeriod;
    private String memo;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}

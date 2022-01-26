package com.cereal.bookrental.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

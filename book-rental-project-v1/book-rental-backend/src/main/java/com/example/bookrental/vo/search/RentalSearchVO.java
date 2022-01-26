package com.example.bookrental.vo.search;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RentalSearchVO {

    private long seq;
    private long bookSeq;
    private long customerSeq;
    private Timestamp rentalStartDate;
    private Timestamp rentalEndDate;
    private Timestamp returnStartDate;
    private Timestamp returnEndDate;
    private int isReturn;
    private Timestamp returnedStartDate;
    private Timestamp returnedEndDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}

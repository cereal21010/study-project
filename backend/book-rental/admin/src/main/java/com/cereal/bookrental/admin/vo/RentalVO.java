package com.cereal.bookrental.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
public class RentalVO {

    private long seq;
    private long bookSeq;
    private long customerSeq;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private int isReturn;
    private Timestamp returnedDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public RentalVO(){}

    public RentalVO(BookVO bookVO, long customerSeq){
        this.customerSeq = customerSeq;
        this.bookSeq = bookVO.getSeq();
        this.returnDate = _convertDate(bookVO.getRentalPeriod());
    }

    //DB시간이랑 안 맞아서 만들어봄...(안 된다..)
    private Timestamp _convertDate(int periodDate) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime.getTime());
        calendar.add(Calendar.DATE, periodDate);
        System.out.println(new Timestamp(calendar.getTime().getTime()));
        return new Timestamp(calendar.getTime().getTime());
    }

}

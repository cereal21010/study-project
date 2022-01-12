package com.example.bookrentalbackend.vo.response;

import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.RentalVO;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Map;

@Getter
@Setter
public class ResponseRentalVO {
    private long bookSeq;
    private String bookName;
    private boolean isReturn;
    private String rentalDate;
    private String returnDate;

    public ResponseRentalVO(RentalVO rentalVO) {
        this.bookSeq = rentalVO.getBookSeq();
        this.isReturn = 0 == rentalVO.getIsReturn() ? false : true;
        this.rentalDate = new SimpleDateFormat("yyyy-MM-dd").format(rentalVO.getRentalDate());
        this.returnDate = new SimpleDateFormat("yyyy-MM-dd").format(rentalVO.getReturnDate());
    }

}

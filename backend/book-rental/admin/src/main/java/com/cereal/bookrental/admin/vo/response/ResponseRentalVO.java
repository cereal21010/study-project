package com.cereal.bookrental.admin.vo.response;

import com.cereal.bookrental.admin.vo.RentalVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ResponseRentalVO {
    private long seq;
    private long bookSeq;
    private String bookName;
    private boolean isReturn;
    private String rentalDate;
    private String returnDate;

    //TODO: response 객체를 따로 보내지 않고, 가능하면 데이터 포멧은 프론트에서 가공해서 사용

    public ResponseRentalVO(RentalVO rentalVO) {
        this.seq = rentalVO.getSeq();
        this.bookSeq = rentalVO.getBookSeq();
        this.isReturn = 0 == rentalVO.getIsReturn() ? false : true;
        this.rentalDate = new SimpleDateFormat("yyyy-MM-dd").format(rentalVO.getRentalDate());
        this.returnDate = new SimpleDateFormat("yyyy-MM-dd").format(rentalVO.getReturnDate());
    }

    public boolean isLate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String current = format.format(currentTime);

        if (this.isReturn) {    //반환하지 않았다면 연체 됐는지 체크
            return false;
        } else {
            return current.compareTo(this.returnDate) > 0 ? true : false;
        }
    }

    public long getLateFee() throws ParseException {
        if (this.isReturn) {
            return 0;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            String current = format.format(currentTime);

            Date currentDate = format.parse(current);
            Date returnDate = format.parse(this.returnDate);

            long diff = currentDate.getTime() - returnDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffDays < 0 ? 0 : diffDays * 100;
        }

    }

}

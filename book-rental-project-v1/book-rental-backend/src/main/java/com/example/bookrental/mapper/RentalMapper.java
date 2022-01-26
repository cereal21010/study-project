package com.example.bookrental.mapper;

import com.example.bookrental.vo.RentalVO;
import com.example.bookrental.vo.search.RentalSearchVO;

import java.util.List;

public interface RentalMapper {

    public List<RentalVO> getRentalList(RentalSearchVO rentalSearchVO);
    public List<RentalVO> findRentalByCustomerSeq(long customerSeq);
    public void insertRental(RentalVO rentalVO);
    public void updateRental(long rentalSeq);
    public int findLateCustomer(long customerSeq);
}

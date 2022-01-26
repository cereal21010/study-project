package com.cereal.bookrental.user.mapper;

import com.cereal.bookrental.user.vo.RentalVO;
import com.cereal.bookrental.user.vo.search.RentalSearchVO;

import java.util.List;

public interface RentalMapper {

    public List<RentalVO> getRentalList(RentalSearchVO rentalSearchVO);
    public List<RentalVO> findRentalByCustomerSeq(long customerSeq);
    public void insertRental(RentalVO rentalVO);
    public void updateRental(long rentalSeq);
    public int findLateCustomer(long customerSeq);
}

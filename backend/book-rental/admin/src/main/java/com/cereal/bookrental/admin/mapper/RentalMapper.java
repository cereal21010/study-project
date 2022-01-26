package com.cereal.bookrental.admin.mapper;

import com.cereal.bookrental.admin.vo.RentalVO;
import com.cereal.bookrental.admin.vo.search.RentalSearchVO;

import java.util.List;

public interface RentalMapper {

    public List<RentalVO> getRentalList(RentalSearchVO rentalSearchVO);
    public List<RentalVO> findRentalByCustomerSeq(long customerSeq);
    public void insertRental(RentalVO rentalVO);
    public void updateRental(long rentalSeq);
    public int findLateCustomer(long customerSeq);
}

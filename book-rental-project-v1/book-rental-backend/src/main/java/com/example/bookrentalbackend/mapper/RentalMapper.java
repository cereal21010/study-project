package com.example.bookrentalbackend.mapper;

import com.example.bookrentalbackend.vo.RentalVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;

import java.util.List;

public interface RentalMapper {

    public List<RentalVO> getRentalList(RentalSearchVO rentalSearchVO);
    public void insertRental(RentalVO rentalVO);
    public void updateRental(long retalSeq);
}

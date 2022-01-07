package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.mapper.BookMapper;
import com.example.bookrentalbackend.mapper.RentalMapper;
import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.RentalVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalMapper rentalMapper;
    private final BookMapper bookMapper;

    public List<RentalVO> getRentalList(RentalSearchVO searchVO) {
        return rentalMapper.getRentalList(searchVO);
    }

    public void insertRental(BookVO bookVO, long customerSeq) {
        BookVO findBookVO = bookMapper.findBookBySeq(bookVO.getSeq());
        RentalVO rentalVO = new RentalVO(findBookVO, customerSeq);
        rentalMapper.insertRental(rentalVO);
    }

    public void updateRental(long rentalSeq) {
        rentalMapper.updateRental(rentalSeq);
    }

}

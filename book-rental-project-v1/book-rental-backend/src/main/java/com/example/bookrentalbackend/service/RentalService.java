package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.mapper.BookMapper;
import com.example.bookrentalbackend.mapper.RentalMapper;
import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.RentalVO;
import com.example.bookrentalbackend.vo.response.ResponseRentalVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalMapper rentalMapper;
    private final BookMapper bookMapper;
    private final PointService pointService;
    private final CustomerService customerService;

    public List<RentalVO> getRentalList(RentalSearchVO searchVO) {
        return rentalMapper.getRentalList(searchVO);
    }

    public List<ResponseRentalVO> getResponseRental(RentalSearchVO searchVO) {
        List<RentalVO> rentalVOS = rentalMapper.getRentalList(searchVO);
        List<ResponseRentalVO> responseRentalVOS = rentalVOS.stream().map(ResponseRentalVO::new).collect(Collectors.toList());
        responseRentalVOS.forEach( vo -> {
            String bookName = bookMapper.findBookBySeq(vo.getBookSeq()).getName();
            vo.setBookName(bookName);
        });
        return responseRentalVOS;
    }

    public void insertRental(BookVO bookVO, long customerSeq) {
        BookVO findBookVO = bookMapper.findBookBySeq(bookVO.getSeq());
        RentalVO rentalVO = new RentalVO(findBookVO, customerSeq);
        rentalMapper.insertRental(rentalVO);
    }

    public void updateRental(long rentalSeq) {
        rentalMapper.updateRental(rentalSeq);
    }

    @Transactional
    public void rentalBook(BookVO bookVO, String customerId) {
        BookVO findBookVO = bookMapper.findBookBySeq(bookVO.getSeq());
        CustomerVO findCustomerVO = customerService.findCustomerById(customerId);

        RentalVO rentalVO = new RentalVO(findBookVO, findCustomerVO.getSeq());
        rentalMapper.insertRental(rentalVO);

        pointService.usePoint(rentalVO.getSeq(), customerId, bookVO.getRentalFee());

    }

}

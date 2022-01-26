package com.cereal.bookrental.admin.service;

import com.cereal.bookrental.admin.exception.ApiException;
import com.cereal.bookrental.admin.exception.ExceptionEnum;
import com.cereal.bookrental.admin.mapper.BookMapper;
import com.cereal.bookrental.admin.mapper.RentalMapper;
import com.cereal.bookrental.admin.vo.BookVO;
import com.cereal.bookrental.admin.vo.CustomerVO;
import com.cereal.bookrental.admin.vo.RentalVO;
import com.cereal.bookrental.admin.vo.response.ResponseRentalVO;
import com.cereal.bookrental.admin.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    //TODO: 다른 Mapper를 사용해야할 경우, Mapper를 바라보는 Service를 사용하도록 지향
    private final RentalMapper rentalMapper;
    private final BookMapper bookMapper;
    private final PointService pointService;
    private final CustomerService customerService;

    public List<RentalVO> getRentalList(RentalSearchVO searchVO) {
        return rentalMapper.getRentalList(searchVO);
    }

    public List<ResponseRentalVO> getResponseRental(long customerSeq) {
        List<RentalVO> rentalVOS = rentalMapper.findRentalByCustomerSeq(customerSeq);
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

    public void updateReturn(long rentalSeq) {
        rentalMapper.updateRental(rentalSeq);
    }

    @Transactional
    public void rentalBook(BookVO bookVO, String customerId) {
        BookVO findBookVO = bookMapper.findBookBySeq(bookVO.getSeq());
        CustomerVO findCustomerVO = customerService.findCustomerById(customerId);
        if (findLateCustomer(customerId)) {
            throw new ApiException(ExceptionEnum.LATE_RETURN);
        }

        RentalVO rentalVO = new RentalVO(findBookVO, findCustomerVO.getSeq());
        rentalMapper.insertRental(rentalVO);

        pointService.usePoint(rentalVO.getSeq(), customerId, bookVO.getRentalFee());

    }

    public boolean findLateCustomer(String customerId) {
        CustomerVO customerVO = customerService.findCustomerById(customerId);
        int lateCount = rentalMapper.findLateCustomer(customerVO.getSeq());
        return lateCount > 0;
    }

}

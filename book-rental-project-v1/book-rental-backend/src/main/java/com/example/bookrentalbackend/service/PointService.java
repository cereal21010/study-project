package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.exception.ApiException;
import com.example.bookrentalbackend.exception.ExceptionEnum;
import com.example.bookrentalbackend.mapper.CustomerMapper;
import com.example.bookrentalbackend.mapper.PointMapper;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.PointVO;
import com.example.bookrentalbackend.vo.search.PointSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    final private PointMapper pointMapper;
    final private CustomerService customerService;

    public List<PointVO> getPointList(PointSearchVO pointSearchVO) {

        String customerId = pointSearchVO.getCustomerId();

        if(customerId != null){
            if ( !"".equals(customerId) ) {
                CustomerVO customerVO = customerService.findCustomerById(customerId);
                if (customerVO != null) {
                    pointSearchVO.setCustomerSeq(customerVO.getSeq());
                } else {    // 검색한 고객 ID가 없는 경우 빈 리스트 객체를 반환한다
                    return new ArrayList<>();
                }
            }
        }

        return  pointMapper.getPointList(pointSearchVO);
    }

    public List<PointVO> getPointListByCustomer(String customerId) {
        CustomerVO customerVO = customerService.findCustomerById(customerId);
        return pointMapper.findPointListByCustomer(customerVO.getSeq());
    }

    public int getRemainPoint(String customerId) {
        CustomerVO customerVO = customerService.findCustomerById(customerId);
        PointVO pointVO = pointMapper.findPointByCustomer(customerVO.getSeq());
        if (pointVO != null) {
            return pointVO.getRemainPoint();
        } else {
            return 0;
        }
    }

    public void chargePoint(String customerId, int chargePoint) {

        PointVO pointVO = new PointVO();

        CustomerVO customerVO = customerService.findCustomerById(customerId);

        PointVO beforePointVOS = pointMapper.findPointByCustomer( customerVO.getSeq() );

        if (beforePointVOS == null) {
            pointVO.setRemainPoint(chargePoint);
            pointVO.setBeforePoint(0);
        } else {
            pointVO.setRemainPoint(beforePointVOS.getRemainPoint() + chargePoint);
            pointVO.setBeforePoint(beforePointVOS.getRemainPoint());
        }

        pointVO.setCustomerSeq(customerVO.getSeq());
        pointVO.setState("charge");
        pointVO.setPointAmount(chargePoint);


        pointMapper.insertPoint(pointVO);

    }

    public void usePoint(long rentalSeq, String customerId, int usePoint){
        CustomerVO customerVO = customerService.findCustomerById(customerId);
        PointVO beforePointVO = pointMapper.findPointByCustomer( customerVO.getSeq() );
        if (beforePointVO == null) {
            throw new ApiException(ExceptionEnum.LACK_POINT);
        }
        _pointValidation(beforePointVO.getRemainPoint(), usePoint);

        PointVO pointVO = new PointVO();
        pointVO.setRentalSeq(rentalSeq);
        pointVO.setCustomerSeq(customerVO.getSeq());
        pointVO.setState("use");
        pointVO.setPointAmount(usePoint);
        pointVO.setRemainPoint(beforePointVO.getRemainPoint() - usePoint);
        pointVO.setBeforePoint(beforePointVO.getRemainPoint());

        pointMapper.insertPoint(pointVO);
    }

    private void _pointValidation(int remainPoint, int usePoint) {
        if ((remainPoint - usePoint) < 0) {
            throw new ApiException(ExceptionEnum.LACK_POINT);
        }
    }

}

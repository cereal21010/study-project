package com.cereal.bookrental.admin.service;

import com.cereal.bookrental.admin.mapper.PointMapper;
import com.cereal.bookrental.admin.vo.CustomerVO;
import com.cereal.bookrental.admin.vo.PointVO;
import com.cereal.bookrental.admin.vo.search.PointSearchVO;
import com.cereal.bookrental.admin.exception.ApiException;
import com.cereal.bookrental.admin.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        PointVO point = new PointVO();

        CustomerVO customer = customerService.findCustomerById(customerId);

        PointVO beforePoint = pointMapper.findPointByCustomer( customer.getSeq() );

        if (beforePoint == null) {
            point.setRemainPoint(chargePoint);
            point.setBeforePoint(0);
        } else {
            point.setRemainPoint(beforePoint.getRemainPoint() + chargePoint);
            point.setBeforePoint(beforePoint.getRemainPoint());
        }
        // (-마이너스) (-마이너스)일 경우 체크 (수정)
        point.setCustomerSeq(customer.getSeq());
        point.setState("charge");
        point.setPointAmount(chargePoint);


        pointMapper.insertPoint(point);

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

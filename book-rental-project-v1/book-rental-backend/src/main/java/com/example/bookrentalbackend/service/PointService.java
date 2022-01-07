package com.example.bookrentalbackend.service;

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
    final private CustomerMapper customerMapper;

    public List<PointVO> getPointList(PointSearchVO pointSearchVO) {

        String customerId = pointSearchVO.getCustomerId();

        if(customerId != null){
            if ( !"".equals(customerId) ) {
                CustomerVO customerVO = customerMapper.findCustomerById(customerId);
                if (customerVO != null) {
                    pointSearchVO.setCustomerSeq(customerVO.getSeq());
                } else {    // 검색한 고객 ID가 없는 경우 빈 리스트 객체를 반환한다
                    return new ArrayList<>();
                }
            }
        }

        return  pointMapper.getPointList(pointSearchVO);
    }

    public PointVO getPointDetail(String customerId) {
        CustomerVO customerVO = customerMapper.findCustomerById(customerId);
        return pointMapper.findPointByIdLastOrder(customerVO.getSeq());
    }

    public void insertPoint(PointVO pointVO, String customerId) {

        CustomerVO customerVO = customerMapper.findCustomerById(customerId);

        PointVO beforePointVO = pointMapper.findPointByIdLastOrder( customerVO.getSeq() );
        pointVO.setBeforePoint( beforePointVO.getRemainPoint() );

        if ( "use".equals(pointVO.getState()) ) {
            pointVO.setRemainPoint(beforePointVO.getRemainPoint() - pointVO.getPointAmount());
        }
        else if ( "charge".equals(pointVO.getState()) ) {
            pointVO.setRemainPoint( beforePointVO.getRemainPoint() + pointVO.getPointAmount() );
        }

        pointMapper.insertPoint(pointVO);
    }

}

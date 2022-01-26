package com.cereal.bookrental.user.controller;

import com.cereal.bookrental.user.exception.ApiException;
import com.cereal.bookrental.user.exception.ExceptionEnum;
import com.cereal.bookrental.user.service.PointService;
import com.cereal.bookrental.user.vo.PointVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/point")
public class PointController {

    private final PointService pointService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getPointList(HttpServletRequest request) {

        String customerId = request.getHeader("Customer-Id");
        if (customerId == null || "null".equals(customerId) || "".equals(customerId)) {
            throw new ApiException(ExceptionEnum.SECURITY_04);
        }
        //토큰을 해독해서 사용
//        String testCustomer = "tid01";
        List<PointVO> pointVOS = pointService.getPointListByCustomer(customerId);

        return new ResponseEntity(pointVOS, HttpStatus.OK);
    }

    //TODO: token 값을 체크하는 로직을 annotation으로 만든 후, 메소드에 붙여서 검사하는 방식으로 활용
    @RequestMapping(value = "/insert/{customerId}", method = RequestMethod.POST)
    public ResponseEntity insertPoint(@PathVariable(value = "customerId") String loginId
                                    , @RequestBody int chargePoint
                                    , HttpServletRequest request) {

        log.info("insertPoint {}, {}", chargePoint, loginId);

        String customerId = request.getHeader("Customer-Id");
        if (customerId == null || "null".equals(customerId) || "".equals(customerId)) {
            throw new ApiException(ExceptionEnum.SECURITY_04);
        }

//        String testCustomer = "tid01";

        pointService.chargePoint( customerId, chargePoint );

        return new ResponseEntity(HttpStatus.OK);
    }

/*    @RequestMapping(value = "/remain")
    public ResponseEntity getPointDetail(String customerId) {

        log.info(" getPointDetail customerId : " + customerId);

        String testCustomer = "tid01";
        PointVO pointVO = pointService.getPointDetail(testCustomer);

        return new ResponseEntity(pointVO.getRemainPoint(), HttpStatus.OK);
    }*/

}

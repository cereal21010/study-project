package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.service.PointService;
import com.example.bookrentalbackend.vo.PointVO;
import com.example.bookrentalbackend.vo.search.PointSearchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/point")
public class PointApi {

    private final PointService pointService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getPointList(String customerId) {

        log.info(" getPointList customerId : " + customerId);

        String testCustomer = "tid01";
        List<PointVO> pointVOS = pointService.getPointListByCustomer(testCustomer);

        return new ResponseEntity(pointVOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert/{customerId}", method = RequestMethod.POST)
    public ResponseEntity insertPoint(@PathVariable(value = "customerId") String customerId
                                    , @RequestBody int chargePoint ) {

        log.info("insertPoint {}, {}", chargePoint, customerId);

        String testCustomer = "tid01";

        pointService.chargePoint( testCustomer, chargePoint );

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

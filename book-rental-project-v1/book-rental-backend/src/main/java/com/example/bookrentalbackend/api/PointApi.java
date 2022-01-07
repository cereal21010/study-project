package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.service.PointService;
import com.example.bookrentalbackend.vo.PointVO;
import com.example.bookrentalbackend.vo.search.PointSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/point")
public class PointApi {

    private final PointService pointService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getPointList(PointSearchVO pointSearchVO) {

        List<PointVO> pointVOS = pointService.getPointList(pointSearchVO);

        return new ResponseEntity(pointVOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity insertPoint(@RequestBody PointVO pointVO) {

        String testCustomer = "tid01";

        pointService.insertPoint(pointVO, testCustomer);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/remain")
    public ResponseEntity getPointDetail() {
        String testCustomer = "tid01";
        PointVO pointVO = pointService.getPointDetail(testCustomer);

        return new ResponseEntity(pointVO.getRemainPoint(), HttpStatus.OK);
    }

}

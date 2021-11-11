package com.example.v2_board.api;

import com.example.v2_board.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/recommend")
public class RecommendApi {

    private final RecommendService recommendService;

    @RequestMapping(value = "/getCount/{boardSeq}", method = RequestMethod.GET)
    public ResponseEntity getRecommendCount(@PathVariable("boardSeq") int boardSeq) {

        int recommendCount = 0;
        try {
            recommendCount = recommendService.getReCommendCount(boardSeq);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(recommendCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity insertRecommend(@RequestBody Map<String, String> requestMap) {

        try {
            recommendService.insertRecommend(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity deleteRecommend(@RequestBody Map<String, String> requestMap) {

//        int testMemberSeq = 2; //test data
        try {
            recommendService.deleteRecommend(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

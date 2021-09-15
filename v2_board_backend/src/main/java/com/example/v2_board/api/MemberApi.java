package com.example.v2_board.api;

import com.example.v2_board.service.MemberSerivce;
import com.example.v2_board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberApi {

    private final MemberSerivce memberSerivce;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerMember(@RequestPart("requestBody")MemberVO vo) {

        log.info("-- api member register --");

        try {
            memberSerivce.insertMember(vo);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}

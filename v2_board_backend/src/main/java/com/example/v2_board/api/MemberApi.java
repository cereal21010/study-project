package com.example.v2_board.api;

import com.example.v2_board.Exception.ApiException;
import com.example.v2_board.common.enums.ExceptionEnum;
import com.example.v2_board.service.login.JwtService;
import com.example.v2_board.service.login.MemberService;
import com.example.v2_board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberApi {

    private final MemberService memberService;
    private final JwtService jwtService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginMember(@RequestBody MemberVO vo, HttpServletResponse response) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            MemberVO memberVO = memberService.loginMember(vo.getMemberId(), vo.getMemberPassword());
            //로그인 성공했다면 토큰을 생성한다.
            String token = jwtService.create(memberVO);
            response.setHeader("jwt-auth-token", token);

            resultMap.put("status", true);
            resultMap.put("memberInfo", memberVO);
            status = HttpStatus.ACCEPTED;
        } catch (ApiException e) {
//            log.error("로그인 실패", e);
//            resultMap.put("message", e.getMessage());
            throw new ApiException(e.getError());
        }
        return new ResponseEntity(resultMap, status);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerMember(@RequestBody MemberVO vo) {

        log.info("-- api member register --");

        try {
            memberService.insertMember(vo);
        } catch (ApiException e) {
            throw new ApiException(e.getError());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getMemberInfo(@RequestParam("memberId") String memberId) {

        MemberVO memberVO = memberService.findById(memberId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("memberInfo", memberVO);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateMember(@RequestBody MemberVO vo) {

        log.info(" -- api member update -- ");

        try {
            memberService.updateMember(vo);
        } catch (Exception e) {
            throw new ApiException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}

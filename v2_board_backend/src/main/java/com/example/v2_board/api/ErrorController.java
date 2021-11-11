package com.example.v2_board.api;

import com.example.v2_board.Exception.ApiException;
import com.example.v2_board.common.enums.ExceptionEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity apiError(HttpServletRequest request) throws Exception {

        System.out.println("에러 컨트롤러!!!");

        ExceptionEnum errorEnum = (ExceptionEnum) request.getAttribute("errorEnum");

        throw new ApiException(errorEnum);


    }
}

package com.example.v2_board.interceptor;

import com.example.v2_board.Exception.ApiException;
import com.example.v2_board.common.enums.ExceptionEnum;
import com.example.v2_board.service.login.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getMethod() + " : " + request.getServletPath());

        //option 요청은 바로 통과시켜주자.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            //request의 parameter에서 auth_token으로 넘어온 녀셕을 찾아본다.
            //String token = request.getParameter("auth_token");
            String token = request.getHeader("jwt-auth-token");
            if (token != null && !"null".equals(token) && token.length() > 0) {
                //유효한 토큰이면 진행, 그렇지 않으면 예외를 발생시킨다.
                try {
                    jwtService.checkValid(token);
                } catch (Exception e) {
//                    forwardSetting(ExceptionEnum.SECURITY_03, request, response);
                    throw new ApiException(ExceptionEnum.SECURITY_03);
                }
                log.trace("토큰 사용 가능: {}", token);
            } else {
//                forwardSetting(ExceptionEnum.SECURITY_02, request, response);
                throw new ApiException(ExceptionEnum.SECURITY_02);
            }

            return true;
        }
    }

    private void forwardSetting(ExceptionEnum exceptionEnum, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorEnum", exceptionEnum);
        request.getRequestDispatcher("/api/error").forward(request, response);
    }

}

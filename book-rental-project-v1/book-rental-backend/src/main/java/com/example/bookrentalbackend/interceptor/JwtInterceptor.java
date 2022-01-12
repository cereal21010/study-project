package com.example.bookrentalbackend.interceptor;

import com.example.bookrentalbackend.exception.ApiException;
import com.example.bookrentalbackend.exception.ExceptionEnum;
import com.example.bookrentalbackend.service.login.JwtService;
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

        log.info(request.getMethod() + " : " + request.getServletPath());

        //option 요청은 통과
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        } else {
            //request의 parameter에서 Authorization을 찾는다.
            String token = request.getHeader("Authorization");
            if (token != null && !"null".equals(token) && token.length() > 0) {
                //유효한 토큰이면 진행, 그렇지 않으면 예외 발생
                try {
                    jwtService.checkValid(token);
                } catch (Exception e) {
//                    forwardSetting(ExceptionEnum.SECURITY_03, request, response);
                    throw new ApiException(ExceptionEnum.SECURITY_03);
                }
                log.info("토큰 사용 가능: {}", token);
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

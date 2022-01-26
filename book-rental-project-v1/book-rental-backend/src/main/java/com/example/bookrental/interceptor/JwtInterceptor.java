package com.example.bookrental.interceptor;

import com.example.bookrental.exception.ExceptionEnum;
import com.example.bookrental.exception.ApiException;
import com.example.bookrental.service.login.JwtService;
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
            
            utilCheckToken(token);  //토큰 null 체크
            
            //유효한 토큰이면 진행, 그렇지 않으면 예외 발생
            try {
                jwtService.checkValid(token);
            } catch (Exception e) {
//                    forwardSetting(ExceptionEnum.SECURITY_03, request, response);
                throw new ApiException(ExceptionEnum.SECURITY_03);
            }
            log.info("토큰 사용 가능: {}", token);
            
            
            return true;
        }
    }

    private void forwardSetting(ExceptionEnum exceptionEnum, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorEnum", exceptionEnum);
        request.getRequestDispatcher("/api/error").forward(request, response);
    }

    private void utilCheckToken(String token) {
        //TODO: StringUtils(apache) 사용해보기 ( dependency 추가해야 됨)
        if (token != null && !"null".equals(token) && token.length() > 0) {
            
        } else {
            throw new ApiException(ExceptionEnum.SECURITY_02);
        }
    }

}

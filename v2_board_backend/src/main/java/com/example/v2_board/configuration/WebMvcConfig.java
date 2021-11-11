package com.example.v2_board.configuration;

import com.example.v2_board.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns(Arrays.asList("/test" /*"/api/board/update", "/api/board/delete/**"*/))  //기본 적용 경로
                .excludePathPatterns(Arrays.asList("/api/member/**", "/api/user/**", "/api/error"));    // 적용 제외 경로
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("== WebMvcConfig 실행 !! ==");

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .exposedHeaders("content-disposition", "jwt-auth-token")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                );
    }
}

package com.example.bookrentalbackend.service.login;

import com.example.bookrentalbackend.vo.CustomerVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    @Value("MYSALT")
    private String salt;

    @Value("60")
    private long expireMin;

    public String create(final CustomerVO customerVO) {
        log.info("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("typ", "JWT");
        builder.setSubject("login token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("User", customerVO);
        builder.signWith(SignatureAlgorithm.HS256, salt);

        final String jwt = builder.compact();
        log.info("토큰 발생: {}", jwt);
        return jwt;
    }

    public void checkValid(final String jwt) {
        log.info("토큰 점검: {}", jwt);
        Jwts.parser().setSigningKey(salt).parseClaimsJws(jwt);
    }

    //JWT분석 후 필요한 정보 반환
    public Map<String, Object> get(final String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        log.info("claims: {}", claims);
        return claims.getBody();
    }

}

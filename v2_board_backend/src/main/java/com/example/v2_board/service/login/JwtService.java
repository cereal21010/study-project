package com.example.v2_board.service.login;

import com.example.v2_board.vo.MemberVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    @Value("${spring.jwt.salt}")
    private String salt;

    @Value("${spring.jwt.expmin}")
    private Long expireMin;

    //JWT을 생성해서 반환
    public String create(final MemberVO vo) {
        log.trace("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("typ", "JWT");
        builder.setSubject("로그인 토큰")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("User", vo);
        builder.signWith(SignatureAlgorithm.HS256, salt);

        final String jwt = builder.compact();
        log.debug("토큰 발행: {}", jwt);
        return jwt;
    }

    //토큰을 확인
    public void checkValid(final String jwt) {
        log.trace("토큰 점검: {}", jwt);
        Jwts.parser().setSigningKey(salt).parseClaimsJws(jwt);
    }

    //JWT분석 후 필요한 정보 반환
    public Map<String, Object> get(final String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        log.trace("claims: {}", claims);
        //Claims는 Map의 구현체이다.
        return claims.getBody();
    }

}

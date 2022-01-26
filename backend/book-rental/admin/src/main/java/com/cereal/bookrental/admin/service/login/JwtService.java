package com.cereal.bookrental.admin.service.login;

import com.cereal.bookrental.admin.vo.AdminVO;
import com.cereal.bookrental.admin.vo.CustomerVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    //TODO: security key 값은 properties등 다른 파일에서 참조하는 형태로 사용
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
                .claim("UserId", customerVO.getId())
                .claim("UserName", customerVO.getName());
        builder.signWith(SignatureAlgorithm.HS256, salt); //TODO: JWT 버전에 따라 key로 만드는 문자열 길이에 제한이 있을 수 있다(확인해야 함)

        final String jwt = builder.compact();
        log.info("토큰 발생: {}", jwt);
        return jwt;
    }

    public String createAdmin(final AdminVO adminVO) {
        log.info("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("typ", "JWT");
        builder.setSubject("login token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("UserId", adminVO.getId());
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

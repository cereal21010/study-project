package com.cereal.bookrental.user.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),

    SECURITY_01(HttpStatus.UNAUTHORIZED, "S0001", "권한이 없습니다."),
    SECURITY_02(HttpStatus.UNAUTHORIZED, "S0002", "인증 토큰이 없습니다."),
    SECURITY_03(HttpStatus.UNAUTHORIZED, "S0003", "인증 토큰이 만료 되었습니다."),
    SECURITY_04(HttpStatus.UNAUTHORIZED, "S0004", "로그인 정보가 상실 되었습니다."),

    NOT_FIND_EMAIL(HttpStatus.BAD_REQUEST, "U0001", "이메일을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "U0002", "비밀번호가 틀립니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U0003", "이미 가입한 이메일 입니다."),

    LACK_POINT(HttpStatus.BAD_REQUEST, "P0001", "포인트가 부족합니다."),

    LATE_RETURN(HttpStatus.LOCKED, "R0001", "미납된 도서가 존재합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}

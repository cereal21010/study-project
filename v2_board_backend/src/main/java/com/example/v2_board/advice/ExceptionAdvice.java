package com.example.v2_board.advice;

import com.example.v2_board.Exception.ApiException;
import com.example.v2_board.common.enums.ExceptionEnum;
import com.example.v2_board.common.response.ApiExceptionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity exceptionHandler(HttpServletRequest request, final ApiException e) {
        System.out.println("exception advice : api exception");
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build()
                );
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity exceptionHandler(HttpServletRequest request, final Exception e) {
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

}

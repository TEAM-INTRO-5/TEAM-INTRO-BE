package com.fastcampus05.zillinks.core.exception;

import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

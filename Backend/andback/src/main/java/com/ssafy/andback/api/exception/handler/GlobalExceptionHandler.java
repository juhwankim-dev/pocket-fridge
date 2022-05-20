package com.ssafy.andback.api.exception.handler;

import com.ssafy.andback.api.dto.response.ErrorResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.ssafy.andback.api.constant.ErrorCode.DUPLICATE_RESOURCE;

/**
*
* GlobalExceptionHandler
* 에러 처리 Handler
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-28
* 마지막 수정일 2022-04-28
**/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /* hibernate 관련 exception 처리 */
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponseDto> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponseDto.toResponseEntity(DUPLICATE_RESOURCE);
    }

    /* Custom한 Exception 처리 */
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }
}

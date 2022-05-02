package com.ssafy.andback.api.dto.response;

import lombok.Data;

import java.util.List;
/**
*
* ListResponseDto
* 리스트 ResponseDto
*
* @author hoony
* @version 1.0.0
* 생성일 2022-05-02
* 마지막 수정일 2022-05-02
**/

public class ListResponseDto<E> extends BaseResponseDto {

    List<E> data;

    public ListResponseDto(List<E> data) {
        this.data = data;
    }

    public ListResponseDto (int status, String message, List<E> data) {
        super(status, message);
        this.data = data;
    }
}

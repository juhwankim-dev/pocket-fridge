package com.ssafy.andback.api.dto.response;

import lombok.Data;

import java.util.List;

@Data
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

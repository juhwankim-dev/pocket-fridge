package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class InsertRefrigeratorReqDto {

    @ApiParam(value = "유저 이메일", example = "test@gmail.com")
    String userEmail;

    @ApiParam(value = "냉장고 이름", example = "실온냉장고")
    String refrigeratorName;

}

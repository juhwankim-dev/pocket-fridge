package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InsertShareMemberRequestDto {

    @ApiParam(value = "냉장고 아이디", example = "1")
    @NotNull
    private Long RefrigeratorId;

}

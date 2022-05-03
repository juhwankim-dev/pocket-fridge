package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
*
* FcmMessageRequestDto
* fcm 메세지 요청 Dto
*
* @author hoony
* @version 1.0.0
* 생성일 2022-05-03
* 마지막 수정일 2022-05-03
**/

@Data
public class FcmMessageRequestDto {

    @ApiParam(value = "타겟 토큰", required = true)
    String targetToken;

    @ApiParam(value = "제목", required = true)
    String title;

    @ApiParam(value = "내용", required = true)
    String body;


}

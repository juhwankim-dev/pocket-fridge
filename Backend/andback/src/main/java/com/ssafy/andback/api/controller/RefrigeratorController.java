package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.response.ListResponseDto;
import com.ssafy.andback.api.dto.response.RefrigeratorResponseDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.RefrigeratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * RefrigeratorController
 * 냉장공 API 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Api(tags = {"03. 냉장고 API"})
@RestController()
@RequestMapping(value = "/refrigerator")
@RequiredArgsConstructor
public class RefrigeratorController {

    private final RefrigeratorService refrigeratorService;

    @ApiOperation(value = "냉장고 조회", notes = "사용자의 냉장고 리스트를 보여준다")
    @GetMapping("/{userEmail}")
    public ResponseEntity<ListResponseDto<RefrigeratorResponseDto>> findRefrigeratorList(@ApiParam(value = "유저 이메일", required = true, example = "test@google") @PathVariable String userEmail) {

        List<RefrigeratorResponseDto> response = refrigeratorService.findAllRefrigeratorByUser(userEmail);

        return ResponseEntity.ok(new ListResponseDto<RefrigeratorResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "냉장고 생성", notes = "사용자의 새로운 냉장고를 생성한다")
    @PostMapping
    public ResponseEntity<BaseResponseDto> insertRefrigerator(@RequestBody InsertRefrigeratorRequestDto reqDto) {

        String response = refrigeratorService.insertRefrigerator(reqDto);


        if (response.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "실패"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "success"));
    }

}

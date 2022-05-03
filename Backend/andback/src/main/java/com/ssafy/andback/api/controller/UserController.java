package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.response.CheckUserResponseDto;
import com.ssafy.andback.api.dto.response.SingleResponseDto;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.FoodIngredientRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRepository;
import io.swagger.annotations.*;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * UserController
 * 유저와 관련된 api를 모은 Controller
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-05-02
 **/

@RequiredArgsConstructor
@Api(value = "유저 API", tags = {"01. 유저 API"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRefrigeratorRepository userRefrigeratorRepository;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private FoodIngredientRepository foodIngredientRepository;

    @ApiOperation(value = "회원가입", notes = "유저 정보를 받아 DB에 저장한다.")
    @PostMapping
    public ResponseEntity<BaseResponseDto> signUp(@RequestBody @Valid UserDto userDto, @ApiIgnore Errors errors) {
        String result = userService.insertUser(userDto);
        if (errors.hasErrors() || result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "회원가입 실패"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "회원가입 성공"));
    }

    @ApiOperation(value = "이메일 중복 검사", notes = "유저 이메일 중복 검사")
    @GetMapping("/checkemail/{userEmail}")
    public ResponseEntity<BaseResponseDto> checkUserEmail(@PathVariable String userEmail) {
        String result = userService.checkUserEmail(userEmail);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일 중복"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "이메일 사용 가능"));
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "유저 닉네임 중복 검사")
    @GetMapping("/checknickname/{userNickname}")
    public ResponseEntity<BaseResponseDto> checkUserNickname(@PathVariable String userNickname) {
        String result = userService.checkUserNickname(userNickname);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "닉네임 중복"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "닉네임 사용 가능"));
    }

    @ApiOperation(value = "이메일 인증번호 전송", notes = "회원가입 시 이메일 인증번호 전송")
    @GetMapping(value = "/{userEmail}")
    public ResponseEntity<SingleResponseDto<String>> sendUserEmailNumber(@PathVariable String userEmail) {
        String userEmailNumber = userService.sendUserEmailNumber(userEmail);

        return ResponseEntity.ok(new SingleResponseDto<>(200, "이메일 인증번호 전송 완료", userEmailNumber));
    }

    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호로 로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<SingleResponseDto<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        String result = userService.login(loginRequestDto);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(new SingleResponseDto<String>(401, "아이디와 비밀번호를 확인해주세요.", null));
        }

        return ResponseEntity.ok(new SingleResponseDto<>(200, "로그인 성공", result));
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기 시 새 비밀번호를 이메일에 전송한다.")
    @PutMapping(value = "/findpassword/{userEmail}")
    public ResponseEntity<BaseResponseDto> findUserPassword(@PathVariable String userEmail) {
        String result = userService.findUserPassword(userEmail);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일을 확인해주세요."));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "새 비밀번호 전송 완료"));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "jwt 토큰을 받아 회원정보를 삭제한다.")
    @Transactional  // org.springframework.dao.InvalidDataAccessApiUsageException 처리
    // Transaction이 Required 되지 않아서 발생하는 것
    @DeleteMapping
    // Authentication 객체: 인증에 성공한 사용자의 정보를 가지고 있는 객체
    public ResponseEntity<BaseResponseDto> deleteUser(@ApiIgnore Authentication authentication) {
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        User user = (User) authentication.getPrincipal();

        // 현재 유저의 모든 Refrigerator 삭제
        List<UserRefrigerator> list;
        list = userRefrigeratorRepository.findUserRefrigeratorByUser(user); // 유저가 가진 모든 유저냉장고 가져오기
        // 연관관계 매핑 순서대로 삭제 (FoodIngredient => Refrigerator의 자식, UserRefrigerator => User의 자식 이므로 FoodIngredient와 UserRefrigerator를 먼저 지운다)
        for (UserRefrigerator userRefrigerator : list) {
            // 해당 냉장고가 가진 식재료 모두 삭제
            foodIngredientRepository.deleteFoodIngredientsByRefrigerator(userRefrigerator.getRefrigerator());
            // 현재 유저의 모든 userRefrigerator 삭제
            userRefrigeratorRepository.deleteUserRefrigeratorByRefrigerator(userRefrigerator.getRefrigerator());
            // 해당 냉장고 id 값을 가진 냉장고 삭제
            refrigeratorRepository.deleteRefrigeratorByRefrigeratorId(userRefrigerator.getUserRefrigeratorId());
        }

        // 현재 유저 삭제
        userRepository.deleteUserByUserId(user.getUserId());

        return ResponseEntity.ok(BaseResponseDto.of(200, "회원 탈퇴 성공"));
    }

    @ApiOperation(value = "회원정보 조회", notes = "유저의 정보를 조회한다.")
    @GetMapping
    public ResponseEntity<SingleResponseDto<CheckUserResponseDto>> checkUser(@ApiIgnore Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        CheckUserResponseDto checkUserResponseDto = CheckUserResponseDto.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userPicture(user.getUserPicture())
                .build();

        return ResponseEntity.ok(new SingleResponseDto<CheckUserResponseDto>(200, "회원 정보 조회 성공", checkUserResponseDto));
    }

//    @ApiOperation(value = "비밀번호 확인", notes = "회원정보 수정 전 비밀번호 확인을 한다.")
//    @PostMapping(value = "/checkpw")

//    @ApiOperation(value = "회원정보 수정", notes = "유저의 정보를 수정한다.")
//    @PutMapping
//    public ResponseEntity<BaseResponseDto> updateUser(@ApiIgnore Authentication authentication, UserDto userDto){
//        String result = userService
//    }



}

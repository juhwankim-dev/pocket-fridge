package com.ssafy.andback.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode
 * Custom ErrorCode
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-28
 * 마지막 수정일 2022-04-28
 **/

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    NULL_PASSWORD(HttpStatus.BAD_REQUEST, "Password 값이 null 입니다."),
    FAIL_CHANGE_INGREDIENT(HttpStatus.BAD_REQUEST, "식재료 수정 실패"),
    FAIL_SAVE_TOKEN(HttpStatus.BAD_REQUEST, "토큰 저장에 실패했습니다"),
    FAIL_LIKE(HttpStatus.BAD_REQUEST, "좋아요 저장 실패"),
    FAIL_REMOVE_LIKE(HttpStatus.BAD_REQUEST, "좋아요 삭제 실패"),
    FAIL_SHARE_GROUP(HttpStatus.BAD_REQUEST, "냉장고 공유 그룹 생성 실패"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 불일치합니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자입니다."),

    /* 404 NOT_FOUND : resource 를 찾을 수 없음 */
    NOT_AUTH_TOKEN(HttpStatus.NOT_FOUND, "토큰 정보가 없습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 레시피를 찾을 수 없습니다."),
    RECIPE_PROCESS_NOT_FOUND(HttpStatus.NOT_FOUND, "레시피 과정 정보를 찾을 수 없습니다."),
    RECIPE_INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "레시피 재료 정보를 찾을 수 없습니다."),
    REFRIGERATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "냉장고 정보를 찾을 수 없습니다."),
    RECOMMEND_REFRIGERATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "추천 레시피 정보를 찾을 수 없습니다."),


    /* 409 : CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이메일이 중복되었습니다"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

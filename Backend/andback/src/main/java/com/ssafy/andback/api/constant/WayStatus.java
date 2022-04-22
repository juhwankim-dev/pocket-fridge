/**
* WayStatus
* 보관 방법 강제를 위해 Enum 생성
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/04/19
**/
package com.ssafy.andback.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WayStatus {
    실온, 냉장, 냉동
}

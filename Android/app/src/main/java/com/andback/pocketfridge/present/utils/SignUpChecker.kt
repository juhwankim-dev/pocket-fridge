package com.andback.pocketfridge.present.utils

import android.widget.TextView
import com.andback.pocketfridge.R
import java.util.regex.Pattern
import android.content.res.Resources
import com.andback.pocketfridge.domain.model.CheckResult

object SignUpChecker {
    const val NAME = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*\$"
    const val NICKNAME = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*\$"
    const val PW = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{5,15}\$"

    const val NAME_EMPTY_ERROR = "이름은 필수 입력 값입니다."
    const val NAME_PATTERN_ERROR = "이름은 특수문자를 제외한 2~20자리여야 합니다."

    const val NICKNAME_EMPTY_ERROR = "닉네임은 필수 입력 값입니다."
    const val NICKNAME_PATTERN_ERROR = "닉네임은 특수문자를 제외한 2~10자리여야 합니다."

    const val EMAIL_EMPTY_ERROR = "이메일은 필수 입력 값입니다."
    const val EMAIL_PATTERN_ERROR = "이메일 형식이 올바르지 않습니다."

    const val PW_EMPTY_ERROR = "비밀번호는 필수 입력 값입니다."
    const val PW_PATTERN_ERROR = "비밀번호는 영문자를 포함한 5~15자리여야 합니다."
    const val PW_MATCH_ERROR = "비밀번호가 일치하지 않습니다."

    fun validateName(name: String): CheckResult {
        return when {
            name.isEmpty() -> {
                CheckResult(NAME_EMPTY_ERROR, false)
            }
            Pattern.matches(NAME, name) == false -> {
                CheckResult(NAME_PATTERN_ERROR, false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateNickname(nickname: String): CheckResult {
        return when {
            nickname.isEmpty() -> {
                CheckResult(NICKNAME_EMPTY_ERROR, false)
            }
            Pattern.matches(NICKNAME, nickname) == false -> {
                CheckResult(NICKNAME_PATTERN_ERROR, false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateEmail(email: String): CheckResult {
        val pattern = android.util.Patterns.EMAIL_ADDRESS

        return when {
            email.trim().isEmpty() -> {
                CheckResult(EMAIL_EMPTY_ERROR, false)
            }
            pattern.matcher(email).matches() == false -> {
                CheckResult(EMAIL_PATTERN_ERROR, false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validatePw(pw: String): CheckResult {
        return when {
            pw.isEmpty() -> {
                CheckResult(PW_EMPTY_ERROR, false)
            }
            Pattern.matches(PW, pw) == false -> {
                CheckResult(PW_PATTERN_ERROR, false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateConfirmPw(checkedPw: String, pw: String): CheckResult {
        return when {
            checkedPw == pw -> {
                CheckResult("", true)
            }
            checkedPw.isEmpty() -> {
                CheckResult("", false)
            }
            else -> {
                CheckResult(PW_MATCH_ERROR, false)
            }
        }
    }
}
package com.andback.pocketfridge.present.utils

import android.widget.TextView
import com.andback.pocketfridge.R
import java.util.regex.Pattern
import android.content.res.Resources
import com.andback.pocketfridge.domain.model.CheckResult

object SignUpChecker {
    const val ID = "^[a-zA-Z0-9]*\$"
    const val ID_REQUIRED = "[a-zA-z]"
    const val NICKNAME = "^[a-zA-Z가-힣0-9]*\$"
    const val PW = "^[a-zA-Z0-9!@#$%^&*]*\$"

    const val DEFAULT_ERROR = "사용할 수 없는 문자를 포함하고 있습니다."
    const val ID_LEN_ERROR = "아이디는 5 ~ 15자입니다."
    const val ID_REQUIRED_ALPHABET = "영문자를 포함해야 합니다."
    const val NICKNAME_LEN_ERROR = "닉네임은 2 ~ 10자입니다."
    const val EMAIL_EMPTY_ERROR = "이메일을 입력하십시오."
    const val EMAIL_PATTERN_ERROR = "올바른 이메일 형식이 아닙니다."
    const val PW_LEN_ERROR = "비밀번호는 5 ~ 15자입니다."
    const val PW_REQUIRED_ALPHABET = "영문자를 포함해야 합니다."
    const val PW_MATCH_ERROR = "비밀번호가 일치하지 않습니다."

    fun validateId(id: String): CheckResult {
        return when {
            Pattern.matches(ID, id) == false -> {
                CheckResult(DEFAULT_ERROR, false)
            }
            id.length < 5 || id.length > 15 -> {
                CheckResult(ID_LEN_ERROR, false)
            }
            Pattern.matches(ID_REQUIRED, id) == false -> {
                CheckResult(ID_REQUIRED_ALPHABET, false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateNickname(nickname: String): CheckResult {
        return when {
            Pattern.matches(NICKNAME, nickname) == false -> {
                CheckResult(DEFAULT_ERROR, false)
            }
            nickname.length < 2 || nickname.length > 10 -> {
                CheckResult(NICKNAME_LEN_ERROR, false)
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
            Pattern.matches(PW, pw) == false -> {
                CheckResult(DEFAULT_ERROR, false)
            }
            pw.length < 5 || pw.length > 15 -> {
                CheckResult(PW_LEN_ERROR, false)
            }
            Pattern.matches(PW_REQUIRED_ALPHABET, pw) == false -> {
                CheckResult(PW_REQUIRED_ALPHABET, false)
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
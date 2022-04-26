package com.andback.pocketfridge.present.utils

import android.widget.TextView
import com.andback.pocketfridge.R
import java.util.regex.Pattern
import android.content.res.Resources
import android.util.Log
import com.andback.pocketfridge.domain.model.CheckResult

object SignUpChecker {
    const val NAME = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*\$"
    const val NICKNAME = "^[a-zA-Zㄱ-ㅎ가-힣0-9]*\$"
    const val PW = "^(?=.*[A-Za-z])[A-Za-z0-9]{5,15}\$"

    fun validateName(name: String): CheckResult {
        return when {
            name.isEmpty() -> {
                CheckResult(R.string.name_empty_error, false)
            }
            Pattern.matches(NAME, name) == false -> {
                CheckResult(R.string.name_pattern_error, false)
            }
            else -> {
                CheckResult(R.string.no_error, true)
            }
        }
    }

    fun validateNickname(nickname: String): CheckResult {
        return when {
            nickname.isEmpty() -> {
                CheckResult(R.string.nickname_empty_error, false)
            }
            Pattern.matches(NICKNAME, nickname) == false -> {
                CheckResult(R.string.nickname_pattern_error, false)
            }
            else -> {
                CheckResult(R.string.no_error, true)
            }
        }
    }

    fun validateEmail(email: String): CheckResult {
        val pattern = android.util.Patterns.EMAIL_ADDRESS

        return when {
            email.trim().isEmpty() -> {
                CheckResult(R.string.email_empty_error, false)
            }
            pattern.matcher(email).matches() == false -> {
                CheckResult(R.string.email_pattern_error, false)
            }
            else -> {
                CheckResult(R.string.no_error, true)
            }
        }
    }

    fun validatePw(pw: String): CheckResult {
        return when {
            pw.isEmpty() -> {
                CheckResult(R.string.pw_empty_error, false)
            }
            Pattern.matches(PW, pw) == false -> {
                CheckResult(R.string.pw_pattern_error, false)
            }
            else -> {
                CheckResult(R.string.no_error, true)
            }
        }
    }

    fun validateConfirmPw(checkedPw: String, pw: String): CheckResult {
        return when {
            checkedPw == pw -> {
                CheckResult(R.string.no_error, true)
            }
            checkedPw.isEmpty() -> {
                CheckResult(R.string.no_error, false)
            }
            else -> {
                CheckResult(R.string.pw_match_error, false)
            }
        }
    }
}
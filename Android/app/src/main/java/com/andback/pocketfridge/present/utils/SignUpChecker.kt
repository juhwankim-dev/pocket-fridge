package com.andback.pocketfridge.present.utils

import android.widget.TextView
import com.andback.pocketfridge.R
import java.util.regex.Pattern
import android.content.res.Resources
import com.andback.pocketfridge.domain.model.CheckResult

object SignUpChecker {
    const val ID = "^[a-zA-Z0-9]*\$"
    const val NICKNAME = "^[a-zA-Z가-힣0-9]*\$"
    const val PW = "^[a-zA-Z0-9!@#$%^&*]*\$"

    fun validateId(id: String): CheckResult {
        return when {
            Pattern.matches(ID, id) == false -> {
                CheckResult("Resources.getSystem().getString(R.string.error_msg_default)", false)
            } id.length < 6 || id.length > 15 -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_id_len), false)
            } else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateNickname(nickname: String): CheckResult {
        return when {
            Pattern.matches(NICKNAME, nickname) == false -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_default), false)
            } nickname.isEmpty() || nickname.length > 6 -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_nickname_len), false)
            } else -> {
                CheckResult("", true)
            }
        }
    }

    fun validateEmail(email: String): CheckResult {
        val pattern = android.util.Patterns.EMAIL_ADDRESS

        return when {
            email.trim().isEmpty() -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_email_empty), false)
            }
            pattern.matcher(email).matches() == false -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_email_pattern), false)
            }
            else -> {
                CheckResult("", true)
            }
        }
    }

    fun validatePw(pw: String): CheckResult {
        return when {
            Pattern.matches(PW, pw) == false -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_default), false)
            }
            pw.length < 8 || pw.length > 20 -> {
                CheckResult(Resources.getSystem().getString(R.string.error_msg_pw_len), false)
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
                CheckResult(Resources.getSystem().getString(R.string.error_msg_pw_match), false)
            }
        }
    }
}
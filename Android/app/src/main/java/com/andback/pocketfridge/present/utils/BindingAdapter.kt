package com.andback.pocketfridge.present.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.andback.pocketfridge.R
import com.andback.pocketfridge.present.views.user.UserViewModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setTextWatcher")
    fun setTextWatcher(view: CustomForm, vm: UserViewModel) {
        when(view.getMenuTitle()) {
            "이메일" -> {
                view.editText.addTextChangedListener {
                    vm.emailForSignUp.value = it.toString()
                }
            }
            "비밀번호" -> {
                view.editText.addTextChangedListener {
                    vm.pwForSignUp.value = it.toString()
                }
            }
            "비밀번호 확인" -> {
                view.editText.addTextChangedListener {
                    vm.pwConfirmForSignUp.value = it.toString()
                }
            }
            "이름" -> {
                view.editText.addTextChangedListener {
                    vm.nameForSignUp.value = it.toString()
                }
            }
            "닉네임" -> {
                view.editText.addTextChangedListener {
                    vm.nicknameForSignUp.value = it.toString()
                }
            }
        }
    }
}
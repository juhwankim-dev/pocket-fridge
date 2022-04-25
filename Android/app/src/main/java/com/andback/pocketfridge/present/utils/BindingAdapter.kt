package com.andback.pocketfridge.present.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.andback.pocketfridge.present.views.user.UserViewModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setTextWatcher")
    fun setTextWatcher(view: CustomForm, vm: UserViewModel) {
        view.editText.addTextChangedListener {
            vm.emailForSignUp.value = it.toString()
        }
    }
}
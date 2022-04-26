package com.andback.pocketfridge.present.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.airbnb.lottie.LottieAnimationView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.present.views.user.UserViewModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setLottiePlay")
    fun setLottiePlay(view: LottieAnimationView, isShowLoading: Boolean){
        when(isShowLoading) {
            true -> view.playAnimation()
            false -> view.pauseAnimation()
        }
    }
}
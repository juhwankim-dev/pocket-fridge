package com.andback.pocketfridge.present.utils

import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

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
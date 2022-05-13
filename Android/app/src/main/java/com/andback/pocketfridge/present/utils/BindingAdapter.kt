package com.andback.pocketfridge.present.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.andback.pocketfridge.R
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setLottiePlay")
    fun setLottiePlay(view: LottieAnimationView, isShowLoading: Boolean){
        when(isShowLoading) {
            true -> view.playAnimation()
            false -> view.pauseAnimation()
        }
    }

    @JvmStatic
    @BindingAdapter("bindProfileImage")
    fun bindProfileImage(view: ImageView, src: String?) {
        if(src != null) {
            Glide.with(view.context)
                .load(src)
                .circleCrop()
                .placeholder(R.drawable.ic_profile_default)
                .error(R.drawable.ic_profile_default)
                .into(view)
        } else {
            view.setBackgroundResource(R.drawable.ic_profile_default)
        }
    }
}
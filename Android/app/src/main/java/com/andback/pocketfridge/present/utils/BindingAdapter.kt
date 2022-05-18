package com.andback.pocketfridge.present.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.andback.pocketfridge.BuildConfig
import com.andback.pocketfridge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

    @JvmStatic
    @BindingAdapter("bindImageUrl")
    fun bindImageUrl(view: ImageView, src: String){
        Glide.with(view.context)
            .load(src)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindImageUrlWithRoundCorner")
    fun bindImageUrlWithRoundCorner(view: ImageView, src: String){
        Glide.with(view.context)
            .load(src)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindCategoryImage")
    fun bindCategoryImgeUrl(view: ImageView, id: Int) {
        val src = "${BuildConfig.FIREBASE_CATEGORY_IMG_BASE_URL}icon_sub_category_$id.png"

        Glide.with(view.context)
            .load(src)
            .error(R.drawable.ic_error_category)
            .into(view)
    }
}
package com.andback.pocketfridge.present.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import android.view.View

object Animation {
    private var shortAnimationDuration = Resources.getSystem().getInteger(android.R.integer.config_shortAnimTime)
    private var mediumAnimationDuration = Resources.getSystem().getInteger(android.R.integer.config_mediumAnimTime)
    private var longAnimationDuration = Resources.getSystem().getInteger(android.R.integer.config_longAnimTime)

    fun crossShortFade(inView: View, outView: View) {
        inView.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
        outView.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    outView.visibility = View.GONE
                }
            })
    }

    fun crossFade(inView: View, outView: View) {
        inView.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(mediumAnimationDuration.toLong())
                .setListener(null)
        }
        outView.animate()
            .alpha(0f)
            .setDuration(mediumAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    outView.visibility = View.GONE
                }
            })
    }

    fun crossLongFade(inView: View, outView: View) {
        inView.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(longAnimationDuration.toLong())
                .setListener(null)
        }
        outView.animate()
            .alpha(0f)
            .setDuration(longAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    outView.visibility = View.GONE
                }
            })
    }
}
package com.andback.pocketfridge.present.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.CustomFormBinding

class CustomForm : LinearLayout {
    private var binding = CustomFormBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        getAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        getAttrs(attrs,defStyleAttr)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomForm)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomForm,defStyle,0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray : TypedArray){
        binding.tvMenuTitle.text = typedArray.getText(R.styleable.CustomForm_menu_title)
        binding.etForm.hint = typedArray.getText(R.styleable.CustomForm_hint_msg)
        binding.tvErrorMsg.text = typedArray.getText(R.styleable.CustomForm_error_msg)

        typedArray.recycle()
    }

    fun setErrorMsg(s: String) {
        binding.tvErrorMsg.text = s
    }

    fun removeErrorMsg() {
        binding.tvErrorMsg.text = ""
    }
}
package com.andback.pocketfridge.present.views.user.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepOneBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel

class StepOneFragment : BaseFragment<FragmentStepOneBinding>(R.layout.fragment_step_one) {
    private val viewModel: UserViewModel by activityViewModels()
    private var isValidEmail = false
    private var isValidPw = false
    private var isValidConfirmPw = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initEvent()
    }

    private fun initViewModel() {
        binding.vm = viewModel

        with(viewModel) {
            pageNumber.observe(viewLifecycleOwner) {
                (context as UserActivity).onChangeFragement(it)
            }
        }
    }

    private fun initEvent() {
        binding.customFormStepOneFEmail.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validateEmail(newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidEmail = isValid
                }

                decideActiveButton()
            }
        }

        binding.customFormStepOneFPw.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validatePw(newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidPw = isValid
                }

                decideActiveButton()
            }
        }

        binding.customFormStepOneFPwConfirm.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validateConfirmPw(binding.customFormStepOneFPw.editText.text.toString(), newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidConfirmPw = isValid
                }

                decideActiveButton()
            }
        }
    }

    private fun decideActiveButton() {
        binding.btnStepOneFNext.also {
            if(isValidEmail && isValidPw && isValidConfirmPw) {
                it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_color))
                it.isClickable = true
            } else {
                it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_non_clickable_button))
                it.isClickable = false
            }
        }
    }
}
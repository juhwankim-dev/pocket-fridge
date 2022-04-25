package com.andback.pocketfridge.present.views.user.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepOneBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.PageSet
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
                when (pageNumber.value) {
                    PageSet.STEP_ONE -> (context as UserActivity).onChangeFragement(PageSet.STEP_ONE)
                    PageSet.STEP_TWO -> (context as UserActivity).onChangeFragement(PageSet.STEP_TWO)
                    PageSet.LOGIN -> (context as UserActivity).onChangeFragement(PageSet.LOGIN)
                }
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
                    isValidEmail = isValid
                }

                decideActiveButton()
            }
        }

        binding.customFormStepOneFPwConfirm.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validateConfirmPw(binding.customFormStepOneFPw.editText.toString(), newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidEmail = isValid
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
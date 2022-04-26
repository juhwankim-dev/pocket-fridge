package com.andback.pocketfridge.present.views.user.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepTwoBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel

class StepTwoFragment : BaseFragment<FragmentStepTwoBinding>(R.layout.fragment_step_two) {
    private val viewModel: UserViewModel by activityViewModels()
    private var isValidName = false
    private var isValidNickName = false

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
        binding.customFormStepTwoFName.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validateName(newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidName = isValid
                }

                decideActiveButton()
            }
        }

        binding.customFormStepTwoFNickname.also {
            it.editText.addTextChangedListener { newText ->
                SignUpChecker.validateNickname(newText.toString()).apply {
                    it.setErrorMsg(errorMsg)
                    isValidNickName = isValid
                }

                decideActiveButton()
            }
        }
    }

    private fun decideActiveButton() {
        binding.btnStepTwoFSignup.also {
            if(isValidName && isValidNickName) {
                it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_color))
                it.isClickable = true
            } else {
                it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_non_clickable_button))
                it.isClickable = false
            }
        }
    }
}
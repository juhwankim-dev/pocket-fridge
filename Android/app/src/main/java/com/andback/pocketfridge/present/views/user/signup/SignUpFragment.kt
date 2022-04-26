package com.andback.pocketfridge.present.views.user.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentSignUpBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    private val viewModel: SignViewModel by activityViewModels()

    // 이름, 닉네임, 비밀번호, 비밀번호 확인 유효성
    var isValidName = false
    var isValidNickname = false
    var isValidPw = false
    var isValidPwConfirm = false

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

            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            nicknameErrorMsg.observe(viewLifecycleOwner) {
                binding.tilSignUpFNickname.error = resources.getString(it)
            }
        }
    }

    private fun initEvent() {
        binding.etSignUpFName.addTextChangedListener { newText ->
            SignUpChecker.validateName(newText.toString()).apply {
                binding.tilSignUpFName.error = resources.getString(stringId)
                isValidName = isValid
                checkValidate()
            }
        }

        binding.etSignUpFNickname.addTextChangedListener { newText ->
            SignUpChecker.validateNickname(newText.toString()).apply {
                binding.tilSignUpFNickname.error = resources.getString(stringId)
                isValidNickname = isValid
                checkValidate()
            }
        }

        binding.etSignUpFPw.addTextChangedListener { newText ->
            SignUpChecker.validatePw(newText.toString()).apply {
                binding.tilSignUpFPw.error = resources.getString(stringId)
                isValidPw = isValid
                checkValidate()
            }
        }

        binding.etSignUpFPwConfirm.addTextChangedListener { newText ->
            SignUpChecker.validateConfirmPw(binding.etSignUpFPw.text.toString(), newText.toString())
                .apply {
                    binding.tilSignUpFPwConfirm.error = resources.getString(stringId)
                    isValidPwConfirm = isValid
                    checkValidate()
                }
        }
    }

    private fun checkValidate() {
        if(isValidName && isValidNickname && isValidPw && isValidPwConfirm) {
            binding.btnSignUpFSignup.apply {
                backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_color))
                isEnabled = true
            }
        } else {
            binding.btnSignUpFSignup.apply {
                backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_non_clickable_button))
                isEnabled = false
            }
        }
    }
}
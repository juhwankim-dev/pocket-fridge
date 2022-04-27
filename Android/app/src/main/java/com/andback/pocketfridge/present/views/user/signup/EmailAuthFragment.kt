package com.andback.pocketfridge.present.views.user.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentEmailAuthBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity

class EmailAuthFragment : BaseFragment<FragmentEmailAuthBinding>(R.layout.fragment_email_auth) {
    private val viewModel: SignUpViewModel by activityViewModels()

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

            toastMsgIntType.observe(viewLifecycleOwner) {
                showToastMessage(resources.getString(it))
            }

            emailErrorMsg.observe(viewLifecycleOwner) {
                binding.tilEmailAuthFEmail.error = resources.getString(it.stringId)
            }

            emailAuthNumberErrorMsg.observe(viewLifecycleOwner) {
                binding.tilEmailAuthFAuthNumber.error = resources.getString(it)
            }
        }
    }

    private fun initEvent() {
        binding.etEmailAuthFEmail.addTextChangedListener { newText ->
            SignUpChecker.validateEmail(newText.toString()).apply {
                binding.tilEmailAuthFEmail.error = resources.getString(stringId)
                binding.btnEmailAuthFSendEmail.isEnabled = isValid
            }
        }

        binding.etEmailAuthFAuthNumber.addTextChangedListener { newText ->
            if (newText.toString().length < 10) {
                binding.tilEmailAuthFAuthNumber.error = resources.getString(R.string.email_auth_len_error)
                deactivateButton()
            } else {
                binding.tilEmailAuthFAuthNumber.error = resources.getString(R.string.no_error)
                activateButton()
            }
        }
    }

    private fun deactivateButton() {
        binding.btnEmailAuthFNext.apply {
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_non_clickable_button))
            isEnabled = false
        }
    }

    private fun activateButton() {
        binding.btnEmailAuthFNext.apply {
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_color))
            isEnabled = true
        }
    }
}
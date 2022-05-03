package com.andback.pocketfridge.present.views.user.findpw

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentFindPWBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity

class FindPWFragment : BaseFragment<FragmentFindPWBinding>(R.layout.fragment_find_p_w) {
    private val viewModel: FindPWViewModel by activityViewModels()
    private var isValidName = false
    private var isValidEmail = false

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
        }
    }

    private fun initEvent() {
        binding.etFindPwFName.addTextChangedListener { newText ->
            SignUpChecker.validateName(newText.toString()).apply {
                binding.tilFindPwFName.error = resources.getString(stringId)
                isValidName = isValid
                checkValidate()
            }
        }

        binding.etFindPwFEmail.addTextChangedListener { newText ->
            SignUpChecker.validateEmail(newText.toString()).apply {
                binding.tilFindPwFEmail.error = resources.getString(stringId)
                isValidEmail = isValid
                checkValidate()
            }
        }
    }

    private fun checkValidate() {
        if(isValidName && isValidEmail) {
            binding.btnFindPwFNext.apply {
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.main_color))
                isEnabled = true
            }
        } else {
            binding.btnFindPwFNext.apply {
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_non_clickable_button))
                isEnabled = false
            }
        }
    }
}
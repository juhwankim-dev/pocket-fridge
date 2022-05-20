package com.andback.pocketfridge.present.views.main.mypage.useredit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentConfirmPWBinding
import com.andback.pocketfridge.domain.model.Password
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPWFragment : BaseFragment<FragmentConfirmPWBinding>(R.layout.fragment_confirm_p_w) {
    private val viewModel: ConfirmPWViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initEvent()
        setToolbar()
    }

    private fun setToolbar() {
        binding.tbConfirmPwF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initViewModel() {
        binding.vm = viewModel

        with(viewModel) {
            isCorrectPW.observe(viewLifecycleOwner) {
                if(it) {
                    findNavController().navigate(R.id.action_confirmPWFragment_to_userEditFragment)
                } else {
                    binding.tilConfirmPwFNickname.error = resources.getString(R.string.pw_wrong_error)
                }
            }
        }
    }

    private fun initEvent() {
        binding.btnConfirmPwFAccept.setOnClickListener {
            val pw = binding.etConfirmPwFNickname.text.toString()
            val checkResult = SignUpChecker.validatePw(pw)

            if(checkResult.isValid) {
                viewModel.checkPW(Password(pw))
            } else {
                binding.tilConfirmPwFNickname.error = resources.getString(checkResult.stringId)
            }
        }
    }
}
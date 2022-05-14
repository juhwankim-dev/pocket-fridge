package com.andback.pocketfridge.present.views.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentMyPageBinding
import com.andback.pocketfridge.present.config.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        binding.vm = viewModel
        viewModel.getUser()

        with(viewModel) {
            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            loginType.observe(viewLifecycleOwner) {
                when(it) {
                    "COMMON" -> {
                        findNavController().navigate(R.id.action_myPageFragment_to_confirmPWFragment)
                    }
                    "GOOGLE" -> {
                        findNavController().navigate(R.id.action_myPageFragment_to_userEditFragment)
                    }
                    "KAKAO" -> {
                        findNavController().navigate(R.id.action_myPageFragment_to_userEditFragment)
                    }
                    else -> {
                        showToastMessage(resources.getString(R.string.network_error))
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.llMyPageFAlarmSetting.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_notiSettingFragment)
        }
        binding.llMyPageFFridgeManagement.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_fridgeManageFragment)
        }

        binding.llMyPageFUserEdit.setOnClickListener {
            viewModel.readLoginType()
        }
    }
}
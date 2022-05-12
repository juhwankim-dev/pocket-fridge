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
        }
    }

    private fun initView() {
        binding.llMyPageFAlarmSetting.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_notiSettingFragment)
        }
    }
}
package com.andback.pocketfridge.present.views.main.mypage.userdelete

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentUserDeleteBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.workmanager.DailyNotiWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDeleteFragment : BaseFragment<FragmentUserDeleteBinding>(R.layout.fragment_user_delete) {
    private val viewModel: UserDeleteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.name = arguments?.getString("name")
        initViewModel()
        initEvent()
        setToolbar()
    }

    private fun initViewModel() {
        binding.vm = viewModel

        with(viewModel) {
            isDeletedUser.observe(viewLifecycleOwner) {
                if(it) {
                    DailyNotiWorker.cancel(requireActivity())
                    viewModel.resetDataStore()
                    viewModel.isLoading.value = false
                    showToastMessage(resources.getString(R.string.delete_user_guide5))
                    logout()
                } else {
                  showToastMessage(resources.getString(R.string.network_error))
                }
            }
        }
    }

    private fun initEvent() {
        binding.chkUserDeleteFAgreement.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.btnUserDeleteF.apply {
                    backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.main_color))
                    isEnabled = true
                }
            } else {
                binding.btnUserDeleteF.apply {
                    backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_non_clickable_button))
                    isEnabled = false
                }
            }
        }
    }

    private fun logout() {
        Intent(activity, UserActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(this)
        }
    }

    private fun setToolbar() {
        binding.tbMyPageF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
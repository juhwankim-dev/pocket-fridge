package com.andback.pocketfridge.present.views.user.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepTwoBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel
import com.andback.pocketfridge.present.views.user.UserViewModel.Companion.LOGIN_PAGE
import com.andback.pocketfridge.present.views.user.UserViewModel.Companion.STEP_ONE_PAGE
import com.andback.pocketfridge.present.views.user.UserViewModel.Companion.STEP_TWO_PAGE

private const val TAG = "StepTwoFragment_juhwan"

class StepTwoFragment : BaseFragment<FragmentStepTwoBinding>(R.layout.fragment_step_two) {
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            pageNumber.observe(viewLifecycleOwner) {
                when (pageNumber.value) {
                    STEP_ONE_PAGE -> (context as UserActivity).onChangeFragement(STEP_ONE_PAGE)
                    STEP_TWO_PAGE -> (context as UserActivity).onChangeFragement(STEP_TWO_PAGE)
                    LOGIN_PAGE -> {
                        // LoginActivity로 이동
                    }
                    else -> Log.d(TAG, "initViewModelCallback: 지원하지 않는 페이지 값이 입력됨")
                }
            }
        }
    }
}
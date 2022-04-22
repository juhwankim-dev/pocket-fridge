package com.andback.pocketfridge.present.views.user.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepOneBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel

class StepOneFragment : BaseFragment<FragmentStepOneBinding>(R.layout.fragment_step_one) {
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fingerSignViewModel = ViewModelProvider(requireActivity()).get(FingerSignViewModel::class.java)
        initEvent()
    }

    private fun initEvent() {
        binding.btnStepOneFNext.setOnClickListener {
            // 유효성 검사 후...
            (context as UserActivity).onChangeFragement(1)
        }
    }
}
package com.andback.pocketfridge.present.views.user.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepTwoBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel

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
                    PageSet.STEP_ONE -> (context as UserActivity).onChangeFragement(PageSet.STEP_ONE)
                    PageSet.STEP_TWO -> (context as UserActivity).onChangeFragement(PageSet.STEP_TWO)
                    PageSet.LOGIN -> (context as UserActivity).onChangeFragement(PageSet.LOGIN)
                }
            }
        }
    }
}
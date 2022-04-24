package com.andback.pocketfridge.present.views.user.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentStepTwoBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.views.user.UserActivity
import com.andback.pocketfridge.present.views.user.UserViewModel

class StepTwoFragment : BaseFragment<FragmentStepTwoBinding>(R.layout.fragment_step_two) {
    lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initEvent()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.vm = viewModel

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

    private fun initEvent() {

    }
}
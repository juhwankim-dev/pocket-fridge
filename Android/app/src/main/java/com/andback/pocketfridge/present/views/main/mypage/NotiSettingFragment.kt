package com.andback.pocketfridge.present.views.main.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentNotiSettingBinding
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.workmanager.DailyNotiWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotiSettingFragment : BaseFragment<FragmentNotiSettingBinding>(R.layout.fragment_noti_setting) {
    private val viewModel: NotiSettingViewModel by viewModels()
    @Inject
    lateinit var readDataStoreUseCase: ReadDataStoreUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.vm = viewModel
        initView()
        setObserver()
    }

    private fun setObserver() {
        with(viewModel) {
            savedHour.observe(viewLifecycleOwner) {
                binding.timepickerNotiSettingF.hour = it
            }
            savedMinute.observe(viewLifecycleOwner) {
                binding.timepickerNotiSettingF.minute = it
            }
            savedAccepted.observe(viewLifecycleOwner) {
                binding.switchNotiSettingF.isChecked = it
            }
        }
    }

    private fun initView() {
        binding.timepickerNotiSettingF.setIs24HourView(true)
        binding.timepickerNotiSettingF.setOnTimeChangedListener { _, hour, min ->
            viewModel.updateHour(hour)
            viewModel.updateMin(min)
        }
        binding.switchNotiSettingF.setOnCheckedChangeListener { _, boolean ->
            viewModel.updateAccepted(boolean)
        }
        binding.btnNotiSettingFSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.save()
                DailyNotiWorker.cancel(requireActivity())
                DailyNotiWorker.runAt(requireActivity(), readDataStoreUseCase)
                requireActivity().runOnUiThread {
                    goBack()
                }
            }
        }
        binding.btnNotiSettingFCancel.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }
}
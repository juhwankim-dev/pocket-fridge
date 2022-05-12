package com.andback.pocketfridge.present.views.main.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentNotiSettingBinding
import com.andback.pocketfridge.present.config.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotiSettingFragment : BaseFragment<FragmentNotiSettingBinding>(R.layout.fragment_noti_setting) {
    private val viewModel: NotiSettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        setObserver()
    }

    private fun setObserver() {
        binding.lifecycleOwner?.let { owner ->
            with(viewModel) {
                savedHour.observe(owner) {
                    binding.timepickerNotiSettingF.hour = it
                }
                savedMinute.observe(owner) {
                    binding.timepickerNotiSettingF.minute = it
                }
                savedAccepted.observe(owner) {
                    binding.switchNotiSettingF.isChecked = it
                }
                savedOffset.observe(owner) {
                    binding.etNotiSettingFExpiryOffset.setText(it.toString())
                }
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
        binding.etNotiSettingFExpiryOffset.addTextChangedListener { text ->
            viewModel.updateOffset(text.toString().toInt())
        }
        binding.btnNotiSettingFSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.save()
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
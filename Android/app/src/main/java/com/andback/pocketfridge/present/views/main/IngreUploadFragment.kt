package com.andback.pocketfridge.present.views.main

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentIngreUploadBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.Storage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngreUploadFragment : BaseFragment<FragmentIngreUploadBinding>(R.layout.fragment_ingre_upload) {
    private val viewModel: IngreUploadViewModel by activityViewModels()
    // TODO: 냉장고 목록 요청
    // TODO: 냉장고 드롭박스 어댑터
    // TODO: app bar navbtn, action 세팅
    // TODO: upload
    // TODO: 입력값 에러처리

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.vm = viewModel
        setObserver()
        setExpiryDateIcon()
        setPurchasedDateIcon()
        setDropDownAdapter()
        setToolbarButton()
    }
    
    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->

            // 에러 관련 live data
                isNameError.observe(owner) {
                    binding.tilIngreUploadFName.error = if(it) getString(R.string.error_msg_ingre_name) else null 
                }
                
                isDateExpiryError.observe(owner) {
                    binding.tilIngreUploadFExpiryDate.error = if(it) getString(R.string.error_msg_ingre_date_expiry) else null
                }
                
                isDatePurchasedError.observe(owner) {
                    binding.tilIngreUploadFPurchasedDate.error = if(it) getString(R.string.error_msg_ingre_date_purchased) else null
                }
                
                isServerError.observe(owner) {
                    // TODO: 서버 통신 실패 ui 처리
                }
                
                isNetworkError.observe(owner) {
                    // TODO: 네트워크 이용 불가 ui 처리
                }

            // 식재료 정보관련 live data
                ingreStorage.observe(owner) {
                    clearStorageTextView()
                    setBrandColorOnText(it)
                }
            }
        }
    }

    private fun clearStorageTextView() {
        val color = ContextCompat.getColor(requireContext(), R.color.gray)
        binding.tvIngreUploadFStorageFridge.setTextColor(color)
        binding.tvIngreUploadFStorageFreeze.setTextColor(color)
        binding.tvIngreUploadFStorageRoom.setTextColor(color)
    }

    private fun setBrandColorOnText(storage: Storage) {
        val color = ContextCompat.getColor(requireContext(), R.color.main_color)
        when(storage) {
            Storage.Fridge -> binding.tvIngreUploadFStorageFridge.setTextColor(color)
            Storage.Freeze -> binding.tvIngreUploadFStorageFreeze.setTextColor(color)
            Storage.Room -> binding.tvIngreUploadFStorageRoom.setTextColor(color)
        }
    }

    private fun setExpiryDateIcon() {
        binding.tilIngreUploadFExpiryDate.setStartIconOnClickListener {
            // TODO: show datepicker 
        }
    }

    private fun setPurchasedDateIcon() {
        binding.tilIngreUploadFPurchasedDate.setStartIconOnClickListener {
            // TODO: show datepicker 
        }
    }

    private fun setDropDownAdapter() {
        // TODO: 냉장고 정보 받아와서 어댑터 세팅 
    }

    private fun setToolbarButton() {
        binding.tbIngreUploadF.setNavigationOnClickListener {
            // TODO: 바코드 찍는 화면으로 돌아가기
        }

        binding.tbIngreUploadF.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.accept_menu_toolbar -> {
                    viewModel.onUploadBtnClick()
                    true
                }
                else -> false
            }
        }
    }
}
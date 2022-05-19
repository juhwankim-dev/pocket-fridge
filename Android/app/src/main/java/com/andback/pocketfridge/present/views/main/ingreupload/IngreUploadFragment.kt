package com.andback.pocketfridge.present.views.main.ingreupload

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.FragmentIngreUploadBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.DateConverter
import com.andback.pocketfridge.present.utils.Storage
import com.andback.pocketfridge.present.views.main.DatePickerFragment
import com.andback.pocketfridge.present.views.main.FridgeListAdapter
import com.andback.pocketfridge.present.views.main.fridge.FridgeListBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngreUploadFragment : BaseFragment<FragmentIngreUploadBinding>(R.layout.fragment_ingre_upload) {
    companion object {
        private const val TAG = "IngreUploadFragment_debuk"
    }

    private val viewModel: IngreUploadViewModel by activityViewModels()
    private val args: IngreUploadFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if(args.isFirst) {
            viewModel.clearData()
        }
        binding.vm = viewModel
        viewModel.clearError()
        setObserver()
        initView()
    }

    private fun initView() {
        setCalendarIconClickListener()
        setCategoryClickListener()
        setToolbarButton()
        setFridgeClickListener()
        args.productName?.let {
            viewModel.name.value = it
        }
        args.subCategory?.let {
            viewModel.selectSubCategory(it)
        }
    }

    private fun setCalendarIconClickListener() {
        binding.tilIngreUploadFExpiryDate.setEndIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.dateExpiry.value = result
            }
        }

        binding.tilIngreUploadFPurchasedDate.setEndIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.datePurchased.value = result
            }
        }
    }

    private fun setFridgeClickListener() {
        binding.tvIngreUploadFFridgeName.setOnClickListener {
            showFridgeDialog()
        }
        binding.ivIngreUploadFFridge.setOnClickListener {
            showFridgeDialog()
        }
    }
    
    private fun setObserver() {
        with(viewModel) {
            // 에러 관련 live data
            isNameError.observe(viewLifecycleOwner) {
                binding.tilIngreUploadFIngreName.error = if(it) getString(R.string.error_msg_ingre_name) else null
            }

            isDateExpiryError.observe(viewLifecycleOwner) {
                binding.tilIngreUploadFExpiryDate.error = if(it) getString(R.string.error_msg_ingre_date_expiry) else null
            }

            isDatePurchasedError.observe(viewLifecycleOwner) {
                binding.tilIngreUploadFPurchasedDate.error = if(it) getString(R.string.error_msg_ingre_date_purchased) else null
            }

            isServerError.observe(viewLifecycleOwner) {
                // TODO: 서버 통신 실패 ui 처리
            }

            isNetworkError.observe(viewLifecycleOwner) {
                // TODO: 네트워크 이용 불가 ui 처리
            }

            isCategoryError.observe(viewLifecycleOwner) {
                if(it) showToastMessage("사진을 클릭하여 카테고리를 선택해 주세요")
            }
            
            selectedSubCategory.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObserver: ${it}")
            }

            isUploadSuccess.observe(viewLifecycleOwner) {
                if(it == true) {
                    showUploadSuccess()
                }
            }
        }
    }

    private fun showUploadSuccess() {
        showToastMessage(resources.getString(R.string.ingre_upload_success))
        viewModel.clearData()
    }

    private fun showDatePickerWith(listener: DatePickerDialog.OnDateSetListener) {
        val datePickerFragment = DatePickerFragment(listener)
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun showCategoryPicker() {
        findNavController().navigate(R.id.action_ingreUploadFragment_to_categorySelectFragment)
    }

    private fun setToolbarButton() {
        binding.tbIngreUploadF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
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

    private fun showFridgeDialog() {
        FridgeListBottomSheet(
            viewModel.fridges.value!!,
            viewModel.selectedFridge.value!!.id
        ).apply {
            fridgeAdapter.itemClickListener = object : FridgeListAdapter.ItemClickListener {
                override fun onClick(data: FridgeEntity) {
                    viewModel.setFridge(data)
                    dismiss()
                }
            }
        }.show(requireActivity().supportFragmentManager, FridgeListBottomSheet.TAG)
    }

    private fun setCategoryClickListener() {
        binding.ivIngreUploadFCategory.setOnClickListener {
            showCategoryPicker()
        }
        binding.ivIngreUploadF.setOnClickListener {
            showCategoryPicker()
        }
    }
}
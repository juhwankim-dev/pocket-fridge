package com.andback.pocketfridge.present.views.main.ingreupload

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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

    // 식재료 선택 다이얼로그와 정보 공유
    private val viewModel: IngreUploadViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.vm = viewModel
        setObserver()
        setDataFromBarcode()
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.init()
    }

    private fun initView() {
        setCalendarIconClickListener()
        setCategoryClickListener()
        setToolbarButton()
        setFridgeClickListener()
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

    override fun onStop() {
        super.onStop()
        viewModel.clearData()
    }
    
    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->

            // 에러 관련 live data
                isNameError.observe(owner) {
                    binding.tilIngreUploadFIngreName.error = if(it) getString(R.string.error_msg_ingre_name) else null
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
            }
        }
    }

    private fun showDatePickerWith(listener: DatePickerDialog.OnDateSetListener) {
        val datePickerFragment = DatePickerFragment(listener)
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun showCategoryPicker() {
        val categorySelectFragment = CategorySelectFragment()
        categorySelectFragment.show(childFragmentManager, "categoryPicker")
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

    private fun setDataFromBarcode() {
        val args: IngreUploadFragmentArgs by navArgs()
        if (args.productName.isNullOrBlank() == false) {
            viewModel.name.value = args.productName
        }
    }
}
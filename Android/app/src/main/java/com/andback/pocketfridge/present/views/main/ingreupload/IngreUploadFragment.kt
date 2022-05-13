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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngreUploadFragment : BaseFragment<FragmentIngreUploadBinding>(R.layout.fragment_ingre_upload) {
    companion object {
        private const val TAG = "IngreUploadFragment_debuk"
    }
    private val viewModel: IngreUploadViewModel by activityViewModels()
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
        setToolbarButton()
        setCategoryClickListener()
        setDataFromBarcode()
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
                selectedStorage.observe(owner) {
                    clearStorageTextView()
                    setBrandColorOnText(it)
                }

                selectedSubCategory.observe(owner) {
                    it?.let {
                        binding.tvIngreUploadFCategory.text = it.subCategoryName    
                    }
                }
                
                selectedMainCategory.observe(owner) {
                    it?.let {
                        // TODO: 이미지 반영 
                    }
                }

//                // 서브카테고리 리스트가 있으면 첫 값을 기본으로 세팅
//                subCategories.observe(owner) {
//                    ingreCategory.value = it[0].subCategoryName
//                }
            // 냉장고 리스트 세팅
                fridges.observe(owner) {
                    setDropDownAdapter(it)
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
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.dateExpiry.value = result
                Log.d(TAG, "setExpiryDateIcon: $result")
            }
        }
    }

    private fun setPurchasedDateIcon() {
        binding.tilIngreUploadFPurchasedDate.setStartIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.datePurchased.value = result
                Log.d(TAG, "setPurchasedDateIcon: $result")
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

    private fun setDropDownAdapter(list: List<FridgeEntity>) {
        val stringList = list.map { it.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_list, stringList)
        (binding.tvIngreUploadFSelectFridge as? AutoCompleteTextView)?.let { tv ->
            tv.setText(stringList[0])
            tv.setAdapter(adapter)
            // 아이템 클릭 시 냉장고 업데이트
            tv.setOnItemClickListener { _, _, i, l ->
                Log.d(TAG, "setDropdownAdapter: $i, $l")
                val fridge = list.find { it.name == stringList[i] }
                fridge?.let { viewModel.setFridge(it) }
            }
        }
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

    private fun setCategoryClickListener() {
        binding.tvIngreUploadFCategory.setOnClickListener {
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
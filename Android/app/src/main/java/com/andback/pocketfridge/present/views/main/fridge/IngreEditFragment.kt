package com.andback.pocketfridge.present.views.main.fridge

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentIngreEditBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.DateConverter
import com.andback.pocketfridge.present.views.main.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngreEditFragment: BaseFragment<FragmentIngreEditBinding>(R.layout.fragment_ingre_edit) {
    private val viewModel: IngreEditViewModel by viewModels()
    private val args: IngreEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.vm = viewModel
        initViewModel()
        setObserver()
        setExpiryDateIcon()
        setPurchasedDateIcon()
        setToolbar()
        setCalendarIconClickListener()
    }

    private fun setCalendarIconClickListener() {
        binding.tilIngreEditFPurchasedDate.setEndIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.datePurchased.value = result
            }
        }

        binding.tilIngreEditFExpiryDate.setEndIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.dateExpiry.value = result
            }
        }
    }

    private fun initViewModel() {
        viewModel.init(args.ingredient)
    }

    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->

                // 에러 관련 live data
                isNameError.observe(owner) {
                    binding.tilIngreEditFIngreName.error = if(it) getString(R.string.error_msg_ingre_name) else null
                }

                isDateExpiryError.observe(owner) {
                    binding.tilIngreEditFExpiryDate.error = if(it) getString(R.string.error_msg_ingre_date_expiry) else null
                }

                isDatePurchasedError.observe(owner) {
                    binding.tilIngreEditFPurchasedDate.error = if(it) getString(R.string.error_msg_ingre_date_purchased) else null
                }

                isServerError.observe(owner) {
                    if(it == true) {
                        showToastMessage(resources.getString(R.string.ingre_edit_error))
                    }
                }

                isNetworkError.observe(owner) {
                    if(it == true) {
                        showToastMessage(resources.getString(R.string.network_error))
                    }
                }

                isUpdateSuccess.observe(owner) {
                    if(it == true) {
                        viewModel.updatedIngredient?.let { ingre ->
                            findNavController().navigate(IngreEditFragmentDirections.actionIngreEditFragmentToIngreDetailFragment(
                                true, ingre
                            ))
                        }
                    }
                }

                selectedSubCategory.observe(owner) {
                    it?.let {
                        // TODO: 카테고리 이미지 변경
                    }
                }

                // 냉장고 리스트 세팅
                fridges.observe(owner) {
//                    setDropDownAdapter(it)
                }
            }
        }
    }

    private fun setExpiryDateIcon() {
        binding.tilIngreEditFExpiryDate.setStartIconOnClickListener {
            showDatePickerWith { _, year, month, day ->
                val result = DateConverter.toStringDate(year, month, day)
                viewModel.dateExpiry.value = result
                Log.d(TAG, "setExpiryDateIcon: $result")
            }
        }
    }

    private fun setPurchasedDateIcon() {
        binding.tilIngreEditFPurchasedDate.setStartIconOnClickListener {
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



//    private fun setDropDownAdapter(list: List<FridgeEntity>) {
//        val stringList = list.map { it.name }
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_list, stringList)
//        (binding.tvIngreEditFSelectFridge as? AutoCompleteTextView)?.let { tv ->
//            tv.setText(stringList[0])
//            tv.setAdapter(adapter)
//            // 아이템 클릭 시 mainCategory 업데이트
//            tv.setOnItemClickListener { _, _, i, l ->
//                Log.d(TAG, "setDropdownAdapter: $i, $l")
//                val fridge = list.find { it.name == stringList[i] }
//                fridge?.let { viewModel.setFridge(it) }
//            }
//        }
//    }

    private fun setToolbar() {
        binding.tbIngreEditF.setNavigationOnClickListener {
            goBack()
        }

        binding.tbIngreEditF.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.accept_menu_toolbar -> {
                    viewModel.onUpdateBtnClick()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    companion object {
        private const val TAG = "IngreEditFragment_debuk"
    }
}

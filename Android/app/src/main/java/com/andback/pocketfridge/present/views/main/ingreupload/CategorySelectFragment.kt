package com.andback.pocketfridge.present.views.main.ingreupload

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.size
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.FragmentCategorySelectBinding
import com.andback.pocketfridge.present.views.main.SubCategoryRVAdapter
import com.google.android.material.chip.Chip

class CategorySelectFragment : DialogFragment() {
    private lateinit var binding: FragmentCategorySelectBinding
    private val rvAdapter = SubCategoryRVAdapter()

    private var isInit = false

    private val viewModel: IngreUploadViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategorySelectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        // TODO: 디바이스 크기에 따라 변경
        val width = 1000
        val height = 1500
        dialog?.window?.setLayout(width, height)
        super.onResume()
    }

    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        setRecyclerViewAdapter()
        setObserver()
        setToolbar()
    }

    private fun setToolbar() {
        binding.tbCategorySelectF.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.close_menu -> {
                    dismiss()
                    true
                }
                else -> false
            }
        }
    }

    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                // 메인 카테고리가 변하면 서브 카테고리 리스트 업데이트
                selectedMainCategory.observe(owner) {
                    updateSelectedSubCategories()
                }

                // 메인 카테고리 리스트로 칩그룹 세팅
                // 메인 카테고리가 UI로 변경하는 경우는 없음
                mainCategories.observe(owner) {

                    if (binding.cgFridgeFFilter.size > 1)
                        binding.cgFridgeFFilter.removeViews(1, binding.cgFridgeFFilter.size-1)

                    it.forEach { category ->
                        val chip = layoutInflater.inflate(R.layout.custom_chip_view, binding.cgFridgeFFilter, false) as Chip
                        chip.id = category.mainCategoryId
                        chip.text = category.mainCategoryName
                        binding.cgFridgeFFilter.addView(chip)
                    }

                    setChipGroupChangeListener(it)
                }

                // 서브 카테고리 리스트로 리사이클러뷰 변경
                // 처음 칩이 전체여서
                selectedSubCategories.observe(owner) {
                    if(isInit == false) {
                        viewModel.selectAllSubCategory()
                        isInit = true
                    } else {
                        updateSubCategories(it)
                    }
                }
            }
        }
    }

    private fun setChipGroupChangeListener(list: List<MainCategoryEntity>) {
        binding.cgFridgeFFilter.setOnCheckedChangeListener { group, checkedId ->
            val result = list.find { it.mainCategoryId == checkedId }
            result?.let { viewModel.selectMainCategory(it) }?: run { viewModel.selectAllSubCategory() }
        }
    }

    private fun setRecyclerViewAdapter() {
        binding.rvCategorySelectFSubCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAdapter.itemClickListener = object : SubCategoryRVAdapter.ItemClickListener {
                override fun onClick(subCategoryEntity: SubCategoryEntity) {
                    viewModel.selectSubCategory(subCategoryEntity)
                    Log.d(TAG, "onClick: ${subCategoryEntity.subCategoryId} ${subCategoryEntity.subCategoryName}")
                    dismiss()
                }
            }
            adapter = rvAdapter
        }
    }

    private fun updateSubCategories(list: List<SubCategoryEntity>) {
        rvAdapter.setItems(list)
    }

    private val TAG = "CategorySelectFragment_debuk"

}
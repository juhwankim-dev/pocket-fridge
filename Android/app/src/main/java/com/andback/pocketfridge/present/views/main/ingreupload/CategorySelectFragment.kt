package com.andback.pocketfridge.present.views.main.ingreupload

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.FragmentCategorySelectBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.SubCategoryRVAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySelectFragment : BaseFragment<FragmentCategorySelectBinding>(R.layout.fragment_category_select) {
    private val rvAdapter = SubCategoryRVAdapter()
    private var isInit = false
    private val viewModel: CategorySelectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.vm = viewModel
        setRecyclerViewAdapter()
        setObserver()
        setToolbar()
        setSearchBar()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchBar() {
        binding.etCategorySelectF.addTextChangedListener {
            if (it != null && it.isNotEmpty()) {
                binding.etCategorySelectF.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_24,
                    0,
                    R.drawable.ic_search_et_right_custom,
                    0)
            } else {
                binding.etCategorySelectF.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_24,
                    0,
                    0,
                    0)
            }
            rvAdapter.filter.filter(it)
        }

        binding.etCategorySelectF.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if(binding.etCategorySelectF.compoundDrawables[2] != null) {
                    if(motionEvent.x >=
                        (binding.etCategorySelectF.right
                                - binding.etCategorySelectF.left
                                - binding.etCategorySelectF.compoundDrawables[2].bounds.width()
                                )
                    ) {
                        if (binding.etCategorySelectF.text.toString().isNotBlank()) {
                            binding.etCategorySelectF.setText("")
                        }
                    }
                }
            }
            false
        }
    }

    private fun setToolbar() {
        binding.tbCategorySelectF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setObserver() {
        with(viewModel) {
            // 메인 카테고리가 변하면 서브 카테고리 리스트 업데이트
            selectedMainCategory.observe(viewLifecycleOwner) {
                updateSelectedSubCategories()
            }

            // 메인 카테고리 리스트로 칩그룹 세팅
            // 메인 카테고리가 UI로 변경하는 경우는 없음
            mainCategories.observe(viewLifecycleOwner) {

                if (binding.cgCategorySelectFFilter.size > 1)
                    binding.cgCategorySelectFFilter.removeViews(1, binding.cgCategorySelectFFilter.size-1)

                it.forEach { category ->
                    val chip = layoutInflater.inflate(R.layout.custom_chip_view, binding.cgCategorySelectFFilter, false) as Chip
                    chip.id = category.mainCategoryId
                    chip.text = category.mainCategoryName
                    binding.cgCategorySelectFFilter.addView(chip)
                }

                setChipGroupChangeListener(it)
            }

            // 서브 카테고리 리스트로 리사이클러뷰 변경
            // 처음 칩이 전체여서
            selectedSubCategories.observe(viewLifecycleOwner) {
                if(isInit == false) {
                    viewModel.selectAllSubCategory()
                    isInit = true
                } else {
                    updateSubCategories(it)
                }
            }
        }
    }

    private fun setChipGroupChangeListener(list: List<MainCategoryEntity>) {
        binding.cgCategorySelectFFilter.setOnCheckedChangeListener { group, checkedId ->
            val result = list.find { it.mainCategoryId == checkedId }
            result?.let { viewModel.selectMainCategory(it) }?: run { viewModel.selectAllSubCategory() }
        }
    }

    private fun setRecyclerViewAdapter() {
        binding.rvCategorySelectFSubCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAdapter.itemClickListener = object : SubCategoryRVAdapter.ItemClickListener {
                override fun onClick(subCategoryEntity: SubCategoryEntity) {
                    Log.d(TAG, "onClick: ${subCategoryEntity.subCategoryId} ${subCategoryEntity.subCategoryName}")

                    // TODO: 이전 화면으로 이동
                    findNavController().navigate(CategorySelectFragmentDirections.actionCategorySelectFragmentToIngreUploadFragment(null, subCategoryEntity, false))
                }
            }
            adapter = rvAdapter
        }
    }

    // 검색어가 있으면 검색어로 필터링
    private fun updateSubCategories(list: List<SubCategoryEntity>) {
        val searchInput = binding.etCategorySelectF.text.toString()
        rvAdapter.setItems(list)
        rvAdapter.filter.filter(searchInput)
    }

    private val TAG = "CategorySelectFragment_debuk"

}
package com.andback.pocketfridge.present.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.FragmentCategorySelectBinding

class CategorySelectFragment : DialogFragment() {
    private lateinit var binding: FragmentCategorySelectBinding
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

    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        setObserver()
    }

    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                // 메인 카테고리가 변하면 서브 카테고리 리스트 업데이트
                selectedMainCategory.observe(owner) {
                    updateSelectedSubCategories()
                }

                // 메인 카테고리 리스트로 어댑터 세팅
                mainCategories.observe(owner) {
                    setDropdownAdapter(it)
                }

                // 서브 카테고리 리스트로 리사이클러뷰 변경
                selectedSubCategories.observe(owner) {
                    setRecyclerViewAdapter(it)
                }
            }
        }
    }

    private fun setDropdownAdapter(list: List<MainCategoryEntity>) {
        val stringList = list.map { it.mainCategoryName }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_fridge_list, stringList)
        (binding.tvCategorySelectFMainCategory as? AutoCompleteTextView)?.let { tv ->
            tv.addTextChangedListener { text ->
                val mainCategory = list.find { it.mainCategoryName == text.toString() }
                // 메인 카테고리 이름으로 FridgeEntity 찾아서 viewmodel에 update
                mainCategory?.let { viewModel.selectMainCategory(it) }
            }
            tv.setAdapter(adapter)
            // 기본값으로 첫번째 FridgeEntity 세팅
            tv.setText(stringList[0])
        }
    }

    private fun setRecyclerViewAdapter(list: List<SubCategoryEntity>) {

    }


}
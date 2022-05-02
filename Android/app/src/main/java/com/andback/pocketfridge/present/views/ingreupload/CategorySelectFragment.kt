package com.andback.pocketfridge.present.views.main

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.FragmentCategorySelectBinding

class CategorySelectFragment : DialogFragment() {
    private lateinit var binding: FragmentCategorySelectBinding
    private val rvAdapter = SubCategoryRVAdapter()

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
                    updateSubCategories(it)
                }
            }
        }
    }

    private fun setDropdownAdapter(list: List<MainCategoryEntity>) {
        val stringList = list.map { it.mainCategoryName }
        Log.d(TAG, "setDropdownAdapter: ${stringList.size}")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_fridge_list, stringList)
        (binding.tvCategorySelectFMainCategory as? AutoCompleteTextView)?.let { tv ->
            tv.setText(stringList[0])
            tv.setAdapter(adapter)
            // 아이템 클릭 시 mainCategory 업데이트
            tv.setOnItemClickListener { _, _, i, l ->
                Log.d(TAG, "setDropdownAdapter: $i, $l")
                val mainCategory = list.find { it.mainCategoryName == stringList[i] }
                mainCategory?.let { viewModel.selectMainCategory(it) }
            }
        }
    }

    private fun setRecyclerViewAdapter() {
        binding.rvCategorySelectFSubCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAdapter.itemClickListener = object : SubCategoryRVAdapter.ItemClickListener {
                override fun onClick(subCategoryEntity: SubCategoryEntity) {
                    viewModel.selectSubCategory(subCategoryEntity)
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
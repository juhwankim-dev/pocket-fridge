package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentIngreDetailBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment

class IngreDetailFragment : BaseFragment<FragmentIngreDetailBinding>(R.layout.fragment_ingre_detail) {
    private val viewModel: IngreDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        arguments?.let {
            viewModel.selectIngre(it["data"] as Ingredient)
        }?: return
        initView()
        setObserver()
        setToolbar()
    }

    private fun setObserver() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                selectedIngre.observe(owner) {
                    setView(it)
                }
                subCategory.observe(owner) {
                    binding.tvIngreDetailFCategory.text = it.subCategoryName
                }
            }
        }
    }
    
    private fun initView() {
        binding.btnIngreDetailFDelete.setOnClickListener { 
            viewModel.deleteIngre()
        }
        binding.btnIngreDetailFEdit.setOnClickListener {
            // TODO: 수정 fragment로 이동 
        }
    }

    private fun setView(ingredient: Ingredient) {
        binding.ingredient = ingredient
        binding.leftDay = when {
            ingredient.leftDay == 0 -> {
                "D-Day"
            }
            ingredient.leftDay < 0 -> {
                "D${ingredient.leftDay}"
            }
            else -> {
                "D+${ingredient.leftDay}"
            }
        }
    }

    private fun setToolbar() {
        binding.tbIngreDetailF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    
    companion object {
        private const val TAG = "IngreDetailFragment_debuk"
    }
}
package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
        initViewModel()
        initView()
        setObserver()
        setToolbar()
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    private fun initViewModel() {
        viewModel.init()
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
                isDeleteSuccess.observe(owner) {
                    if(it == true) {
                        showToastMessage(resources.getString(R.string.ingre_delete_success))
                        goBack()
                    }
                }
                isDeleteFail.observe(owner) {
                    if(it == true) {
                        showToastMessage(resources.getString(R.string.ingre_delete_error))
                        viewModel.init()
                    }
                }
            }
        }
    }
    
    private fun initView() {
        binding.btnIngreDetailFDelete.setOnClickListener { 
            viewModel.deleteIngre()
        }
        binding.btnIngreDetailFEdit.setOnClickListener {
            findNavController().navigate(R.id.action_ingreDetailFragment_to_ingreEditFragment, bundleOf("data" to viewModel.selectedIngre.value))
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
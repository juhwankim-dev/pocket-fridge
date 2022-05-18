package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentIngreDetailBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngreDetailFragment : BaseFragment<FragmentIngreDetailBinding>(R.layout.fragment_ingre_detail) {
    private val viewModel: IngreDetailViewModel by viewModels()
    private val args: IngreDetailFragmentArgs by navArgs()

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
            selectedIngre.observe(viewLifecycleOwner) {
                setView(it)
            }
            subCategory.observe(viewLifecycleOwner) {
                // TODO: 식재료의 서브 카테고리에 따라 이미지 변경
            }
            isDeleteSuccess.observe(viewLifecycleOwner) {
                if(it == true) {
                    showToastMessage(resources.getString(R.string.ingre_delete_success))
                    goBack()
                }
            }
            isDeleteFail.observe(viewLifecycleOwner) {
                if(it == true) {
                    showToastMessage(resources.getString(R.string.ingre_delete_error))
                    viewModel.init()
                }
            }
        }
    }
    
    private fun initView() {
        when(args.isOwner) {
            true -> {
                binding.tbIngreDetailF.menu.findItem(R.id.delete_menu_fridge).isVisible = true
                binding.tbIngreDetailF.menu.findItem(R.id.edit_menu_fridge).isVisible = true
            }
            else -> {
                binding.tbIngreDetailF.menu.findItem(R.id.delete_menu_fridge).isVisible = false
                binding.tbIngreDetailF.menu.findItem(R.id.edit_menu_fridge).isVisible = false
            }
        }
        viewModel.selectIngre(args.ingredient)
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
        // 툴바 메뉴 클릭 리스너
        binding.tbIngreDetailF.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_menu_fridge -> {
                    if(args.isOwner) {
                        showDeleteDialog()
                        true
                    } else {
                        showToastMessage("삭제 권한이 없습니다.")
                        false
                    }
                }
                R.id.edit_menu_fridge -> {
                    if(args.isOwner) {
                        viewModel.selectedIngre.value?.let {
                            findNavController().navigate(
                                IngreDetailFragmentDirections.actionIngreDetailFragmentToIngreEditFragment(
                                    it
                                )
                            )
                        }
                        true
                    } else {
                        showToastMessage("수정 권한이 없습니다.")
                        false
                    }
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_ingre_title))
            .setCancelable(true)
            .setNegativeButton(resources.getString(R.string.cancel_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.delete_button)) { _, _ ->
                viewModel.deleteIngre()
            }
            .show()
    }
    
    companion object {
        private const val TAG = "IngreDetailFragment_debuk"
    }
}
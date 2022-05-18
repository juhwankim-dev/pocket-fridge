package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentIngreDetailBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment

class IngreDetailFragment : BaseFragment<FragmentIngreDetailBinding>(R.layout.fragment_ingre_detail) {
    private val viewModel: IngreDetailViewModel by activityViewModels()
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
            binding.lifecycleOwner?.let { owner ->
                selectedIngre.observe(owner) {
                    setView(it)
                }
                subCategory.observe(owner) {
                    // TODO: 식재료의 서브 카테고리에 따라 이미지 변경
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
        val args: IngreDetailFragmentArgs by navArgs()
        when(args.isOwner) {
            true -> {
                binding.btnIngreDetailFDelete.visibility = View.VISIBLE
                binding.btnIngreDetailFEdit.visibility = View.VISIBLE
            }
            else -> {
                binding.btnIngreDetailFDelete.visibility = View.GONE
                binding.btnIngreDetailFEdit.visibility = View.GONE
            }
        }
        viewModel.selectIngre(args.ingredient)

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
        // 툴바 메뉴 클릭 리스너
        binding.tbIngreDetailF.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_menu_fridge -> {
                    if(args.isOwner) {
                        viewModel.deleteIngre()
                        true
                    } else {
                        showToastMessage("삭제 권한이 없습니다.")
                        false
                    }
                }
                R.id.edit_menu_fridge -> {
                    if(args.isOwner) {
                        findNavController().navigate(R.id.action_ingreDetailFragment_to_ingreEditFragment, bundleOf("data" to viewModel.selectedIngre.value))
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
    
    companion object {
        private const val TAG = "IngreDetailFragment_debuk"
    }
}
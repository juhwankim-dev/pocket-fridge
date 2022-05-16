package com.andback.pocketfridge.present.views.main.recipe.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentDetailRecipeBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.RecipeItemDecoration

class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>(R.layout.fragment_detail_recipe) {
    lateinit var detailRecipeAdapter: DetailRecipeAdapter
    private val viewModel: CookSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
        initEvent()
    }

    private fun initView() {
        detailRecipeAdapter = DetailRecipeAdapter()
        detailRecipeAdapter.setHeaderContent(viewModel.selectedRecipe)

        binding.rvDetailRecipe.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailRecipeAdapter
            addItemDecoration(RecipeItemDecoration(20F, ContextCompat.getColor(context, R.color.gray_divider)))
        }
    }

    private fun initViewModel() {
        binding.selectedRecipe = viewModel.selectedRecipe
        with(viewModel) {
            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            cookingIngres.observe(viewLifecycleOwner) {
                detailRecipeAdapter.setBodyContent(it)
            }
        }
    }

    private fun initEvent() {
        detailRecipeAdapter.setItemClickListener(object : DetailRecipeAdapter.ItemClickListener{
            override fun onAddLikeClick(recipeId: Int) {
                viewModel.addLike(recipeId)
            }

            override fun onDeleteLikeClick(recipeId: Int) {
                viewModel.deleteLike(recipeId)
            }
        })
    }
}
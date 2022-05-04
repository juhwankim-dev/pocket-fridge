package com.andback.pocketfridge.present.views.main.recipe.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentDetailRecipeBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.RecipeItemDecoration

class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>(R.layout.fragment_detail_recipe) {
    lateinit var detailRecipeAdapter: DetailRecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        // API 만들어지기 전까지 임시
        detailRecipeAdapter = DetailRecipeAdapter()
        detailRecipeAdapter.setList(listOf(Recipe(""), Recipe("")))

        binding.rvDetailRecipe.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailRecipeAdapter
            addItemDecoration(RecipeItemDecoration(20F, ContextCompat.getColor(context, R.color.gray_divider)))
        }
    }
}
package com.andback.pocketfridge.present.views.main.recipe

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentRecipeBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseFragment

class RecipeFragment : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {
    lateinit var recipeAdapter: RecipeAdapter
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
    }
    
    private fun initView() {
        // API 만들어지기 전까지 임시
        recipeAdapter = RecipeAdapter()
        recipeAdapter.setList(listOf(Recipe(""), Recipe(""), Recipe(""), Recipe("")))

        binding.rvRecipeF.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
            addItemDecoration(RecipeItemDecoration(20F, ContextCompat.getColor(context, R.color.gray_divider)))
        }
    }
    
    private fun initEvent() {
        recipeAdapter.setItemClickListener(object : RecipeAdapter.ItemClickListener{
            override fun onClick(recipe: Recipe) {
                // TODO: 클릭시 레시피 상세 페이지로 이동
            }
        })
    }
}
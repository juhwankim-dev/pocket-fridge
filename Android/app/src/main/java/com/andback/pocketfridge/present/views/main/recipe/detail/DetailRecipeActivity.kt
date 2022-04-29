package com.andback.pocketfridge.present.views.main.recipe.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityDetailRecipeBinding
import com.andback.pocketfridge.databinding.ActivityMainBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseActivity
import com.andback.pocketfridge.present.views.main.recipe.RecipeAdapter
import com.andback.pocketfridge.present.views.main.recipe.RecipeItemDecoration

class DetailRecipeActivity : BaseActivity<ActivityDetailRecipeBinding>(R.layout.activity_detail_recipe) {
    lateinit var detailRecipeAdapter: DetailRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
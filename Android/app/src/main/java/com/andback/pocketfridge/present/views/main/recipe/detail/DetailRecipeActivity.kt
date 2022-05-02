package com.andback.pocketfridge.present.views.main.recipe.detail

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityDetailRecipeBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseActivity
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.flDetailRecipeF.visibility = View.GONE
            binding.ablDetailRecipeF.visibility = View.VISIBLE
            binding.rvDetailRecipe.visibility = View.VISIBLE
        } else {
            binding.flDetailRecipeF.visibility = View.VISIBLE
            binding.ablDetailRecipeF.visibility = View.GONE
            binding.rvDetailRecipe.visibility = View.GONE

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fl_detail_recipeF)

            if (currentFragment == null) {
                val fragment = RecipeStepsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_detail_recipeF, fragment)
                    .commit()
            }
        }
    }
}
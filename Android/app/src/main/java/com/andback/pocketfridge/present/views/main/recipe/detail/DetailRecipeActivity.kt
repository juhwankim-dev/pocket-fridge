package com.andback.pocketfridge.present.views.main.recipe.detail

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.databinding.ActivityDetailRecipeBinding
import com.andback.pocketfridge.present.config.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRecipeActivity : BaseActivity<ActivityDetailRecipeBinding>(R.layout.activity_detail_recipe) {
    private val viewModel: CookSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        if(intent.hasExtra("recipe")) {
            intent.getParcelableExtra<RecipeEntity>("recipe")!!.let {
                viewModel.selectedRecipe = it
                viewModel.getCookingIngres(it.id)
                viewModel.getRecipeSteps(it.id)
            }
        } else {
            showToastMessage(resources.getString(R.string.network_error))
            finish()
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            isIngresLoaded.observe(this@DetailRecipeActivity) {
                if(it && isStepsLoaded.value == true) {
                    initFragment()
                }
            }

            isStepsLoaded.observe(this@DetailRecipeActivity) {
                if(it && isIngresLoaded.value == true) {
                    initFragment()
                }
            }
        }
    }

    private fun initFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fl_detail_recipeF)

        if (currentFragment == null) {
            val fragment = DetailRecipeFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_detail_recipeF, fragment)
                .commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_detail_recipeF, DetailRecipeFragment())
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_detail_recipeF, CookModeFragment())
                .commit()
        }
    }
}
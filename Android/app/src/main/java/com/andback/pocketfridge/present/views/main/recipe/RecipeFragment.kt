package com.andback.pocketfridge.present.views.main.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.databinding.FragmentRecipeBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.detail.DetailRecipeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {
    lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: RecipeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initView()
        initViewModel()
        initEvent()
    }

    private fun initViewModel() {
        with(viewModel) {
            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            recipes.observe(viewLifecycleOwner) {
                recipeAdapter.setList(it)
            }
        }
    }

    private fun initView() {
        recipeAdapter = RecipeAdapter()

        binding.rvRecipeF.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
            addItemDecoration(RecipeItemDecoration(20F, ContextCompat.getColor(context, R.color.gray_divider)))
        }
    }
    
    private fun initEvent() {
        recipeAdapter.setItemClickListener(object : RecipeAdapter.ItemClickListener{
            override fun onClick(recipe: RecipeEntity) {
                Intent(requireContext(), DetailRecipeActivity::class.java).let {
                    it.putExtra("recipe", recipe)
                    startActivity(it)
                }
            }

            override fun onAddLikeClick(recipeId: Int) {
                viewModel.addLike(recipeId)
            }

            override fun onDeleteLikeClick(recipeId: Int) {
                viewModel.deleteLike(recipeId)
            }
        })
    }
}
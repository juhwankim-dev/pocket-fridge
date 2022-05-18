package com.andback.pocketfridge.present.views.main.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentRecipeBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.detail.DetailRecipeActivity
import com.andback.pocketfridge.present.views.main.recipe.filtered.FilteredRecipeAdapter.Companion.ALL
import com.andback.pocketfridge.present.views.main.recipe.filtered.FilteredRecipeAdapter.Companion.JAPANESE
import com.andback.pocketfridge.present.views.main.recipe.filtered.FilteredRecipeAdapter.Companion.KOREAN
import com.andback.pocketfridge.present.views.main.recipe.filtered.FilteredRecipeAdapter.Companion.LIKE
import com.andback.pocketfridge.present.views.main.recipe.filtered.FilteredRecipeAdapter.Companion.WESTERN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe), onRecipeClickListener {
    lateinit var recipeRecommendAdapter: RecipeRecommendAdapter
    lateinit var recipeAllAdapter: RecipeAllAdapter
    private val viewModel: RecipeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
        initEvent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipes()
    }

    private fun initViewModel() {
        with(viewModel) {
            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            recipes.observe(viewLifecycleOwner) {
                recipeRecommendAdapter.setList(it)
                recipeAllAdapter.setList(it)
            }
        }
    }

    private fun initView() {
        recipeRecommendAdapter = RecipeRecommendAdapter(this)
        recipeAllAdapter = RecipeAllAdapter(this)

        binding.rvRecipeFRecommend.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeRecommendAdapter
        }

        binding.rvRecipeFAll.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = recipeAllAdapter
        }
    }

    fun initEvent() {
        binding.ivRecipeFKorean.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_filteredRecipeFragment, getPutDataBundle(KOREAN))
        }

        binding.ivRecipeFJapanese.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_filteredRecipeFragment, getPutDataBundle(JAPANESE))
        }

        binding.ivRecipeFWestern.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_filteredRecipeFragment, getPutDataBundle(WESTERN))
        }

        binding.ivRecipeFLike.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_filteredRecipeFragment, getPutDataBundle(LIKE))
        }

        binding.tvRecipeFViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_filteredRecipeFragment, getPutDataBundle(ALL))
        }
    }

    private fun getPutDataBundle(keyword: String): Bundle {
        val bundle = Bundle()
        bundle.putString("keyword", keyword)
        return bundle
    }

    override fun onClick(recipe: Recipe) {
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
}
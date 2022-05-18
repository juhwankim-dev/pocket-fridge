package com.andback.pocketfridge.present.views.main.recipe.filtered

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentFilteredRecipeBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.detail.DetailRecipeActivity
import com.andback.pocketfridge.present.views.main.recipe.onRecipeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilteredRecipeFragment : BaseFragment<FragmentFilteredRecipeBinding>(R.layout.fragment_filtered_recipe),
    onRecipeClickListener {
    lateinit var filteredRecipeAdapter: FilteredRecipeAdapter
    private val viewModel: FilteredRecipeViewModel by viewModels()
    var keyword = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyword = arguments?.getString("keyword") ?: "전체"
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
                filteredRecipeAdapter.setList(it, keyword)
            }
        }
    }

    private fun initView() {
        filteredRecipeAdapter = FilteredRecipeAdapter(this)

        binding.rvFilteredRecipeF.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filteredRecipeAdapter
        }

        binding.tbFilteredRecipeF.title = if(keyword == "all") {
            "전체보기"
        } else {
            "$keyword 모아보기"
        }
    }

    private fun initEvent() {
        binding.tbFilteredRecipeF.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
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
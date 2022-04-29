package com.andback.pocketfridge.present.views.main.recipe.detail

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemRecipeDetailIngreListBinding
import com.andback.pocketfridge.databinding.ItemRecipeListBinding
import com.andback.pocketfridge.domain.model.Recipe

class RecipeIngreAdapter : RecyclerView.Adapter<RecipeIngreAdapter.RecipeIngreViewHolder>() {
    private val recipeList: ArrayList<Recipe> = ArrayList()

    inner class RecipeIngreViewHolder(private val binding: ItemRecipeDetailIngreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: Recipe) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngreViewHolder {
        var binding = ItemRecipeDetailIngreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeIngreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeIngreViewHolder, position: Int) {
        holder.apply {
            bindInfo(recipeList[position])

        }
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<Recipe>) {
        recipeList.clear()
        recipeList.addAll(list)
        notifyDataSetChanged()
    }
}
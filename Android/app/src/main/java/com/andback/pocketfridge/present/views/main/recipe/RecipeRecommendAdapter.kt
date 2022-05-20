package com.andback.pocketfridge.present.views.main.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemRecommendListBinding
import com.andback.pocketfridge.domain.model.Recipe

class RecipeRecommendAdapter(val itemClickListener: onRecipeClickListener) : RecyclerView.Adapter<RecipeRecommendAdapter.RecipeViewHolder>() {
    private var recipeList: ArrayList<Recipe> = ArrayList()

    inner class RecipeViewHolder(private val binding: ItemRecommendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: Recipe) {
            binding.index = layoutPosition + 1
            binding.recipe = recipe
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = ItemRecommendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.apply {
            bindInfo(recipeList[position])

            //클릭연결
            itemView.setOnClickListener {
                itemClickListener.onClick(recipeList[position])
            }
        }
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<Recipe>) {
        recipeList.clear()
        list.filter { it.isRecommendation }.forEach { recipeList.add(it) }
        if (recipeList.isEmpty())
            recipeList.addAll(list.asSequence().shuffled().take(5).toList())

        notifyDataSetChanged()
    }
}
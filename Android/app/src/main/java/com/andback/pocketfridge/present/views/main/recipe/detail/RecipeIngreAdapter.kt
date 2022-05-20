package com.andback.pocketfridge.present.views.main.recipe.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemRecipeDetailIngreListBinding
import com.andback.pocketfridge.domain.model.CookingIngre

class RecipeIngreAdapter : RecyclerView.Adapter<RecipeIngreAdapter.RecipeIngreViewHolder>() {
    private val cookingIngreList = arrayListOf<CookingIngre>()

    inner class RecipeIngreViewHolder(private val binding: ItemRecipeDetailIngreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(cookingIngre: CookingIngre) {
            binding.cookingIngre = cookingIngre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngreViewHolder {
        var binding = ItemRecipeDetailIngreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeIngreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeIngreViewHolder, position: Int) {
        holder.apply {
            bindInfo(cookingIngreList[position])
        }
    }

    override fun getItemCount(): Int = cookingIngreList.size

    fun setList(list: List<CookingIngre>) {
        cookingIngreList.clear()
        cookingIngreList.addAll(list)
        notifyDataSetChanged()
    }
}
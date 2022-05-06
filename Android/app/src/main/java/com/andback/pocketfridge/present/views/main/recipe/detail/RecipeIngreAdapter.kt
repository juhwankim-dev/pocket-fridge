package com.andback.pocketfridge.present.views.main.recipe.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.databinding.ItemRecipeDetailIngreListBinding

class RecipeIngreAdapter : RecyclerView.Adapter<RecipeIngreAdapter.RecipeIngreViewHolder>() {
    private val cookingIngreList = arrayListOf<CookingIngreEntity>()

    inner class RecipeIngreViewHolder(private val binding: ItemRecipeDetailIngreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(cookingIngre: CookingIngreEntity) {
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

    fun setList(list: List<CookingIngreEntity>) {
        cookingIngreList.clear()
        cookingIngreList.addAll(list)
        notifyDataSetChanged()
    }
}
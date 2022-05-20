package com.andback.pocketfridge.present.views.main.recipe.filtered

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemRecipeFilteredListBinding
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.present.views.main.recipe.onRecipeClickListener

class FilteredRecipeAdapter(val itemClickListener: onRecipeClickListener) : RecyclerView.Adapter<FilteredRecipeAdapter.RecipeViewHolder>() {
    private var recipeList: ArrayList<Recipe> = ArrayList()

    inner class RecipeViewHolder(private val binding: ItemRecipeFilteredListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: Recipe) {
            binding.recipe = recipe

            binding.ivRecipeFilteredILike.setOnClickListener {
                if (recipe.like) {
                    itemClickListener.onDeleteLikeClick(recipe.id)
                } else {
                    itemClickListener.onAddLikeClick(recipe.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = ItemRecipeFilteredListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun setList(list: List<Recipe>, keyword: String) {
        recipeList.clear()

        when(keyword) {
            KOREAN -> list.filter { it.type == KOREAN }.forEach { recipeList.add(it) }
            JAPANESE -> list.filter { it.type == JAPANESE }.forEach { recipeList.add(it) }
            WESTERN -> list.filter { it.type == WESTERN }.forEach { recipeList.add(it) }
            LIKE -> list.filter { it.like }.forEach { recipeList.add(it) }
            else -> recipeList.addAll(list)
        }

        notifyDataSetChanged()
    }

    companion object {
        const val KOREAN = "한식"
        const val JAPANESE = "일식"
        const val WESTERN = "양식"
        const val LIKE = "좋아요"
        const val ALL = "전체"
    }
}
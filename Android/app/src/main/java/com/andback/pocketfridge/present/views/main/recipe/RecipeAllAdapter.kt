package com.andback.pocketfridge.present.views.main.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemRecipeAllListBinding
import com.andback.pocketfridge.domain.model.Recipe

class RecipeAllAdapter(val itemClickListener: onRecipeClickListener) : RecyclerView.Adapter<RecipeAllAdapter.RecipeViewHolder>() {
    private var recipeList: ArrayList<Recipe> = ArrayList()

    inner class RecipeViewHolder(private val binding: ItemRecipeAllListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: Recipe) {
            binding.recipe = recipe

            binding.ivRecipeAllILike.setOnClickListener {
                if (recipe.like) {
                    itemClickListener.onDeleteLikeClick(recipe.id)
                } else {
                    itemClickListener.onAddLikeClick(recipe.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = ItemRecipeAllListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        for(i in 0..3) {
            recipeList.add(list[i])
        }
        // TODO: 나중에 백엔드에서 식재료 보유순으로 정렬할 수 있는 값을 넣어주면 정렬 추가
        notifyDataSetChanged()
    }
}
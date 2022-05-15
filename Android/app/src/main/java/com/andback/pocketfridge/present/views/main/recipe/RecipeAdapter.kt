package com.andback.pocketfridge.present.views.main.recipe

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ItemRecipeListBinding
import com.andback.pocketfridge.domain.model.Recipe

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(), Filterable {
    private lateinit var itemClickListener: ItemClickListener
    private var recipeList: ArrayList<Recipe> = ArrayList()
    private var recipeListFiltered: ArrayList<Recipe> = ArrayList()
    private val chipIndex = mapOf (
        "전체보기" to -1,
        "즐겨찾기" to 0,
        "추천메뉴" to 1,
        "한식" to 2,
        "양식" to 3,
        "일식" to 4,
    )

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: Recipe) {
            binding.recipe = recipe

            binding.ivRecipeIHeart.setOnClickListener {
                // 좋아요 -> 안좋아요
                if(recipe.like) {
                    binding.ivRecipeIHeart.setImageResource(R.drawable.ic_heart_outline)
                    itemClickListener.onDeleteLikeClick(recipe.id)
                }

                // 안좋아요 -> 좋아요
                else {
                    binding.ivRecipeIHeart.setImageResource(R.drawable.ic_heart_filled)
                    binding.lottieRecipeIHeart.visibility = View.VISIBLE
                    binding.lottieRecipeIHeart.playAnimation()
                    itemClickListener.onAddLikeClick(recipe.id)
                }
            }

            binding.lottieRecipeIHeart.addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.lottieRecipeIHeart.visibility = View.GONE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var binding = ItemRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.apply {
            bindInfo(recipeListFiltered[position])

            //클릭연결
            itemView.setOnClickListener{
                itemClickListener.onClick(recipeListFiltered[position])
            }
        }
    }

    override fun getItemCount(): Int = recipeListFiltered.size

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val category = constraint?.toString()?.toInt()
                val filteredList = ArrayList<Recipe>()

                recipeListFiltered = when(category) {
                    chipIndex["즐겨찾기"] -> {
                        recipeList.filter{ it.like }.forEach{ filteredList.add(it) }
                        filteredList
                    }
                    chipIndex["추천메뉴"] -> {
                        recipeList.filter{ it.isRecommendation }.forEach { filteredList.add(it) }
                        filteredList
                    }
                    chipIndex["한식"] -> {
                        recipeList.filter{ it.type == "한식" }.forEach{ filteredList.add(it) }
                        filteredList
                    }
                    chipIndex["양식"] -> {
                        recipeList.filter{ it.type == "양식" }.forEach{ filteredList.add(it) }
                        filteredList
                    }
                    chipIndex["일식"] -> {
                        recipeList.filter{ it.type == "일식" }.forEach{ filteredList.add(it) }
                        filteredList
                    }
                    else -> {
                        recipeList
                    }
                }

                return FilterResults().apply { values = recipeListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                recipeListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Recipe>
                notifyDataSetChanged()
            }
        }
    }

    fun setList(list: List<Recipe>) {
        recipeList.clear()
        recipeList.addAll(list)
        recipeListFiltered.clear()
        recipeListFiltered.addAll(list)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(recipe: Recipe)
        fun onAddLikeClick(recipeId: Int)
        fun onDeleteLikeClick(recipeId: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
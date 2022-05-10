package com.andback.pocketfridge.present.views.main.recipe

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.databinding.ItemRecipeListBinding

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    private val recipeList: ArrayList<RecipeEntity> = ArrayList()

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipe: RecipeEntity) {
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
            bindInfo(recipeList[position])

            //클릭연결
            itemView.setOnClickListener{
                itemClickListener.onClick(recipeList[position])
            }
        }
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<RecipeEntity>) {
        recipeList.clear()
        recipeList.addAll(list)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(recipe: RecipeEntity)
        fun onAddLikeClick(recipeId: Int)
        fun onDeleteLikeClick(recipeId: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
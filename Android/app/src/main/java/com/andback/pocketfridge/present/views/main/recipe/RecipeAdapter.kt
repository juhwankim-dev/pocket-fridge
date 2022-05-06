package com.andback.pocketfridge.present.views.main.recipe

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.databinding.ItemRecipeListBinding

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    private val recipeList: ArrayList<RecipeEntity> = ArrayList()

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(recipeEntity: RecipeEntity) {
            binding.recipeEntity = recipeEntity

            binding.ivRecipeIHeart.setOnClickListener {
                if(!it.isSelected) {
                    binding.lottieRecipeIHeart.visibility = View.VISIBLE
                    binding.lottieRecipeIHeart.playAnimation()
                }

                it.isSelected = !it.isSelected
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
        fun onClick(recipeId: RecipeEntity)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
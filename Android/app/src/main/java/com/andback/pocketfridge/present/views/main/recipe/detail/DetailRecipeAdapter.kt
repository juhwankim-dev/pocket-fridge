package com.andback.pocketfridge.present.views.main.recipe.detail

import android.animation.Animator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ItemDetailRecipeBodyListBinding
import com.andback.pocketfridge.databinding.ItemDetailRecipeHeaderListBinding
import com.andback.pocketfridge.domain.model.CookingIngre
import com.andback.pocketfridge.domain.model.Recipe

class DetailRecipeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    private val viewHolderType = mapOf (
        "HEADER" to 0,
        "BODY" to 1
    )

    lateinit var recipe: Recipe
    private val cookingIngreList = arrayListOf<CookingIngre>()

    inner class HeadViewHolder(private val binding: ItemDetailRecipeHeaderListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() {
            binding.recipe = recipe
            binding.ivDetailRecipeHeaderIHeart.setOnClickListener {
                // 좋아요 -> 안좋아요
                if(recipe.like) {
                    binding.ivDetailRecipeHeaderIHeart.setImageResource(R.drawable.ic_heart_outline)
                    itemClickListener.onDeleteLikeClick(recipe.id)
                }

                // 안좋아요 -> 좋아요
                else {
                    binding.ivDetailRecipeHeaderIHeart.setImageResource(R.drawable.ic_heart_filled)
                    binding.lottieDetailRecipeHeartIHeart.visibility = View.VISIBLE
                    binding.lottieDetailRecipeHeartIHeart.playAnimation()
                    itemClickListener.onAddLikeClick(recipe.id)
                }
            }

            binding.lottieDetailRecipeHeartIHeart.addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.lottieDetailRecipeHeartIHeart.visibility = View.GONE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }

            })
        }
    }

    inner class BodyViewHolder(private val binding: ItemDetailRecipeBodyListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rvRecipeDetailBodyI.apply {
                adapter = RecipeIngreAdapter()
                layoutManager = LinearLayoutManager(context)

                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            }
        }

        fun bindInfo() {
            binding.serving = recipe.serving
            (binding.rvRecipeDetailBodyI.adapter as RecipeIngreAdapter).apply {
                setList(cookingIngreList)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            viewHolderType["HEADER"] -> {
                val binding = ItemDetailRecipeHeaderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeadViewHolder(binding)
            }
            else -> {
                val binding = ItemDetailRecipeBodyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BodyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            (holder is HeadViewHolder) -> holder.bindInfo()
            (holder is BodyViewHolder) -> holder.bindInfo()
            else -> Unit
        }
    }

    override fun getItemCount(): Int = viewHolderType.size

    fun setHeaderContent(recipe: Recipe) {
        this.recipe = recipe
        notifyDataSetChanged()
    }

    fun setBodyContent(cookingIngres: List<CookingIngre>) {
        cookingIngreList.clear()
        cookingIngreList.addAll(cookingIngres)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onAddLikeClick(recipeId: Int)
        fun onDeleteLikeClick(recipeId: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
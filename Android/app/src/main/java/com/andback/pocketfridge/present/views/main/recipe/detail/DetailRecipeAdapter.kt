package com.andback.pocketfridge.present.views.main.recipe.detail

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.databinding.ItemDetailRecipeBodyListBinding
import com.andback.pocketfridge.databinding.ItemDetailRecipeHeaderListBinding

class DetailRecipeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val CONTENT_TYPE_CNT = 2
    val TYPE_HEADER = 0
    val TYPE_BODY = 1

    lateinit var recipe: RecipeEntity
    private val cookingIngreList = arrayListOf<CookingIngreEntity>()

    inner class HeadViewHolder(private val binding: ItemDetailRecipeHeaderListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() {
            binding.recipe = recipe
            binding.ivDetailRecipeHeaderIHeart.setOnClickListener {
                if(!it.isSelected) {
                    binding.lottieDetailRecipeHeartIHeart.visibility = View.VISIBLE
                    binding.lottieDetailRecipeHeartIHeart.playAnimation()
                }

                it.isSelected = !it.isSelected
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

    // position의 최대값이 TYPE의 개수보다 많지 않도록 주의
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_HEADER -> {
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

    override fun getItemCount(): Int = CONTENT_TYPE_CNT

    fun setHeaderContent(recipe: RecipeEntity) {
        this.recipe = recipe
        notifyDataSetChanged()
    }

    fun setBodyContent(cookingIngres: List<CookingIngreEntity>) {
        //cookingIngreList.clear()
        cookingIngreList.addAll(cookingIngres)
        //notifyDataSetChanged()
    }
}
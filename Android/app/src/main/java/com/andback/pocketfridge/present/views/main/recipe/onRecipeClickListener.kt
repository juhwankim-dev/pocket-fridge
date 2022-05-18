package com.andback.pocketfridge.present.views.main.recipe

import com.andback.pocketfridge.domain.model.Recipe

interface onRecipeClickListener {
    fun onClick(recipe: Recipe)
    fun onAddLikeClick(recipeId: Int)
    fun onDeleteLikeClick(recipeId: Int)
}
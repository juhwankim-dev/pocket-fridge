package com.andback.pocketfridge.data.model

data class RecipeEntity (
    val recipeId: Int,
    val recipeFoodName: String,
    val recipeFoodSummary: String,
    val recipeContent: String,
    val recipeImage: String?,
    val recipeType: String
)
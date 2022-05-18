package com.andback.pocketfridge.data.mapper

import com.andback.pocketfridge.BuildConfig
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.domain.model.Recipe

object RecipeMapper {
    operator fun invoke(recipeList: List<RecipeEntity>, recommendList: List<Int>): ArrayList<Recipe> {
        var newList = arrayListOf<Recipe>()

        recipeList.sortedBy { it.requireCount }.forEach { r ->
            val isRecommendation = recommendList.any { recommendRecipeId: Int ->
                recommendRecipeId == r.id
            }

            val time = r.time.split(" ")[0]
            val difficulty = when(time.toInt()) {
                in 0..20 -> "쉬움"
                in 21..40 -> "보통"
                else -> "어려움"
            }
            val serving = r.serving.split(" ")[0] + "인분"
            val url = BuildConfig.FIREBASE_STORAGE_RECIPE_THUMBNAIL_BASE_URL + r.url

            newList.add(
                Recipe(
                    id = r.id,
                    allIngredients = r.allIngredients,
                    content = r.content,
                    foodName = r.foodName,
                    serving = serving,
                    url = url,
                    time = time,
                    difficulty = difficulty,
                    type = r.type,
                    like = r.like,
                    isRecommendation = isRecommendation
                )
            )
        }

        return newList
    }
}
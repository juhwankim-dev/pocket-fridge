package com.andback.pocketfridge.data.mapper

import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.domain.model.Recipe

object RecipeMapper {
    operator fun invoke(recipeList: List<RecipeEntity>, recommendList: List<Int>): ArrayList<Recipe> {
        var newList = arrayListOf<Recipe>()

        recipeList.forEach { r ->
            val isRecommendation = recommendList.any { recommendRecipeId: Int ->
                recommendRecipeId == r.id
            }
            newList.add(
                Recipe(
                    id = r.id,
                    allIngredients = r.allIngredients,
                    content = r.content,
                    foodName = r.foodName,
                    serving = r.serving,
                    url = r.url,
                    time = r.time,
                    type = r.type,
                    like = r.like,
                    isRecommendation = isRecommendation
                )
            )
        }

        return newList
    }
}
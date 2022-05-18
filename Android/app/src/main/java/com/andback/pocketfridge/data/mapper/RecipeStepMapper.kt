package com.andback.pocketfridge.data.mapper

import com.andback.pocketfridge.BuildConfig
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.domain.model.RecipeStep

object RecipeStepMapper {
    operator fun invoke(recipeStepList: List<RecipeStepEntity>?): ArrayList<RecipeStep> {
        var newList = arrayListOf<RecipeStep>()

        recipeStepList?.forEach { r ->
            val url = BuildConfig.FIREBASE_STORAGE_RECIPE_COOK_STEP_BASE_URL + r.url

            newList.add(
                RecipeStep(
                    step = r.step,
                    explanation = r.explanation,
                    url = url
                )
            )
        }

        return newList
    }
}
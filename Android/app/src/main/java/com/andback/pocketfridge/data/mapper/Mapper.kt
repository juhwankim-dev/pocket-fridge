package com.andback.pocketfridge.data.mapper

import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.utils.Storage

object IngreMapper {
    operator fun invoke(entity: IngreEntity): Ingredient {
        return Ingredient(
            category = entity.subCategoryId,
            purchasedDate = entity.foodIngredientDate,
            expiryDate = entity.foodIngredientExp,
            name = entity.foodIngredientName,
            fridgeId = entity.refrigeratorId,
            storage = Storage.valueOf(entity.foodIngredientWay)
        )
    }

    operator fun invoke(ingredient: Ingredient): IngreEntity {
        return IngreEntity(
            subCategoryId = ingredient.category,
            foodIngredientDate = ingredient.purchasedDate,
            foodIngredientExp = ingredient.expiryDate,
            foodIngredientName = ingredient.name,
            foodIngredientWay = ingredient.storage.value,
            refrigeratorId = ingredient.fridgeId
        )
    }
}
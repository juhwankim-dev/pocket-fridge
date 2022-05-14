package com.andback.pocketfridge.data.mapper

import android.util.Log
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.utils.DateConverter
import com.andback.pocketfridge.present.utils.Storage
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

object IngreMapper {
    private const val TAG = "Mapper_debuk"

    operator fun invoke(entity: IngreEntity): Ingredient {
        return Ingredient(
            id = entity.foodIngredientId,
            subCategory = entity.subCategoryId,
            mainCategory = entity.mainCategoryId,
            purchasedDate = entity.foodIngredientDate,
            expiryDate = entity.foodIngredientExp,
            name = entity.foodIngredientName,
            fridgeId = entity.refrigeratorId,
            storage = Storage.getWithString(entity.foodIngredientWay),
            leftDay = getLeftDay(entity.foodIngredientExp, SimpleDateFormat("yyyy-MM-dd").format(Date()))
        )
    }

    operator fun invoke(ingredient: Ingredient): IngreEntity {
        return IngreEntity(
            foodIngredientId = ingredient.id,
            subCategoryId = ingredient.subCategory,
            foodIngredientDate = ingredient.purchasedDate,
            foodIngredientExp = ingredient.expiryDate,
            foodIngredientName = ingredient.name,
            foodIngredientWay = ingredient.storage.value,
            refrigeratorId = ingredient.fridgeId
        )
    }

    private fun getLeftDay(stringDate: String, nowStringDate: String): Int {
        val inputDate = DateConverter.toDate(stringDate).time
        val nowDate = DateConverter.toDate(nowStringDate).time
        val result = ceil((nowDate - inputDate) / (24 * 60 * 60 * 1000).toDouble()).toInt()
        Log.d(TAG, "getLeftDay: ${result}")
        return result
    }
}
package com.andback.pocketfridge.present.utils

import com.andback.pocketfridge.domain.model.Ingredient
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object IngreChecker {

    // 유효성 검사 체크
    fun check(ingredient: Ingredient) {
        if(!checkQuantity(ingredient)) throw IngreQuantityException()
        if(!checkName(ingredient)) throw IngreNameException()
        if(!checkDatePurchased(Date(), ingredient)) throw IngreDatePurchasedException()
        if(!checkExpiryDate(ingredient)) throw IngreDateExpiryException()
        if(!checkFridge(ingredient)) throw IngreFridgeIdException()
        if(!checkCategory(ingredient)) throw IngreCategoryException()
    }

    fun checkQuantity(ingredient: Ingredient): Boolean {
        return ingredient.quantity > 0
    }

    fun checkName(ingredient: Ingredient): Boolean {
        return !(ingredient.name.isBlank() or ingredient.name.isEmpty())
    }

    fun checkFridge(ingredient: Ingredient): Boolean {
        return ingredient.fridgeId != -1
    }

    fun checkCategory(ingredient: Ingredient): Boolean {
        return ingredient.subCategory != -1
    }

    // String을 Date로 바꾸는 과정에서 에러 캐치
    fun convertDate(dateString: String): Date? {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            formatter.isLenient = false
            val date = formatter.parse(dateString)
            date
        } catch (e: Exception) {
            null
        }
    }


    fun checkDatePurchased(now: Date, ingredient: Ingredient): Boolean {
        convertDate(ingredient.purchasedDate)?.let { date ->
            // 오늘 날짜보다 미래에 구매일을 작성하는 것은 유효하지 않다.
            if(now >= date) {
                return true
            }
        }
        return false
    }

    fun checkExpiryDate(ingredient: Ingredient): Boolean {
        convertDate(ingredient.expiryDate)?.let { expiryDate ->
            // 오늘 구매일보다 유통기한이 이전이면 유효하지 않다.
            convertDate(ingredient.purchasedDate)?.let { purchasedDate ->
                if(expiryDate >= purchasedDate) {
                    return true
                }
            }
        }
        return false
    }
}
package com.andback.pocketfridge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andback.pocketfridge.present.config.INGRE_TABLE

data class IngreEntityForUpload(
    val subCategoryId: Int,
    val foodIngredientName: String,
    val foodIngredientExp: String,
    val foodIngredientDate: String,
    val foodIngredientWay : String,
    val refrigeratorId : Int
)

@Entity(tableName = INGRE_TABLE)
data class IngreEntity(
    val foodIngredientDate: String,
    val foodIngredientExp: String,
    val foodIngredientName: String,
    val foodIngredientWay: String,
    val refrigeratorId: Int,
    val subCategoryId: Int,
    val mainCategoryId: Int = -1,
    @PrimaryKey
    val foodIngredientId: Int = -1,
)


// From server code

// 냉장고 아이디(냉장고 1 : 식재료 N)

//@ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
//@JoinColumn(name = "refrigerator_id")
//private Refrigerator refrigerator;
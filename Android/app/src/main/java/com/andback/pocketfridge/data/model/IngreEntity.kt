package com.andback.pocketfridge.data.model

import com.google.gson.annotations.SerializedName

data class IngreEntityForUpload(
    val category: Int,
    val foodIngredientName: String,
    val foodIngredientExp: String,
    val foodIngredientDate: String,
    val foodIngredientWay : String,
    val refrigeratorId : Int
)
// TODO: SerializedName 설정 필요, fridgeId 타입 체크 필요




// From server code

// 냉장고 아이디(냉장고 1 : 식재료 N)

//@ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
//@JoinColumn(name = "refrigerator_id")
//private Refrigerator refrigerator;
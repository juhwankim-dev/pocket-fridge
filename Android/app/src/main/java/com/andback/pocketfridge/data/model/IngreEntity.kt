package com.andback.pocketfridge.data.model

data class IngreEntityForUpload(
    val name: String,
    val expiryDate: String,
    val purchasedDate: String,
    val fridgeId: Int,
    val quantity: Int = 1,
    val category: String,
    val storage: String
)
// TODO: SerializedName 설정 필요, fridgeId 타입 체크 필요


// From server code

// 냉장고 아이디(냉장고 1 : 식재료 N)

//@ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
//@JoinColumn(name = "refrigerator_id")
//private Refrigerator refrigerator;
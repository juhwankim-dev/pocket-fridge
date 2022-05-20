package com.andback.pocketfridge.data.mapper

import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.LackIngreEntity
import com.andback.pocketfridge.domain.model.CookingIngre
import kotlin.collections.ArrayList

object CookingIngreMapper {
    operator fun invoke(ingreList: List<CookingIngreEntity>, lackList: List<LackIngreEntity>): ArrayList<CookingIngre> {
        var newList = arrayListOf<CookingIngre>()

        ingreList.forEach { i ->
            val isLack = lackList.any { l: LackIngreEntity -> l.name == i.name }
            newList.add(CookingIngre(i.name, i.count, isLack))
        }

        return newList
    }
}
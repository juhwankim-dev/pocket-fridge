package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.present.utils.DateConverter
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetIngreListForNotiUseCase @Inject constructor(
    private val ingreRepository: IngreRepository
) {
    operator fun invoke(offsetFromNow: Int): Single<List<Ingredient>> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance().apply {
            set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DAY_OF_MONTH).plus(offsetFromNow))
        }
        val fromString = SimpleDateFormat("yyyy-MM-dd").format(from.time)
        val toString = SimpleDateFormat("yyyy-MM-dd").format(to.time)
        return ingreRepository.getIngreListByDateBetween(fromString, toString)
    }
}
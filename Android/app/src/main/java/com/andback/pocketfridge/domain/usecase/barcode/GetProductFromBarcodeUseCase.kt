package com.andback.pocketfridge.domain.usecase.barcode

import com.andback.pocketfridge.data.model.BarcodeResponse
import com.andback.pocketfridge.domain.repository.BarcodeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetProductFromBarcodeUseCase @Inject constructor(
    private val barcodeRepository: BarcodeRepository
) {
    operator fun invoke(barcode: String): Single<BarcodeResponse> = barcodeRepository.getProductFromBarcode(barcode)
}
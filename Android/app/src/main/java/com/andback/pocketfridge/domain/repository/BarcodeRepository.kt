package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BarcodeResponse
import io.reactivex.Single

interface BarcodeRepository {
    fun getProductFromBarcode(barcode: String): Single<BarcodeResponse>
}
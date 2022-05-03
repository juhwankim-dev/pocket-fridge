package com.andback.pocketfridge.data.repository.barcode

import com.andback.pocketfridge.data.model.BarcodeResponse
import io.reactivex.Single

interface ProductRemoteDataSource {
    fun getProductFromBarcode(barcode: String): Single<BarcodeResponse>
}
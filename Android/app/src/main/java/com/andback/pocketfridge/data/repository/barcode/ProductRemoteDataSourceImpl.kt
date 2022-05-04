package com.andback.pocketfridge.data.repository.barcode

import com.andback.pocketfridge.data.api.BarcodeApi
import com.andback.pocketfridge.data.model.BarcodeResponse
import io.reactivex.Single
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val barcodeApi: BarcodeApi
) : ProductRemoteDataSource {

    override fun getProductFromBarcode(barcode: String): Single<BarcodeResponse> {
        return barcodeApi.getProductFromBarcode(barcode = barcode)
    }
}
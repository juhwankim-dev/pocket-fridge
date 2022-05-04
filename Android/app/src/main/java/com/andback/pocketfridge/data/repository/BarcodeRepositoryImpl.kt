package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BarcodeResponse
import com.andback.pocketfridge.data.repository.barcode.ProductRemoteDataSource
import com.andback.pocketfridge.domain.repository.BarcodeRepository
import io.reactivex.Single
import javax.inject.Inject

class BarcodeRepositoryImpl @Inject constructor(
    private val dataSource: ProductRemoteDataSource
): BarcodeRepository {
    override fun getProductFromBarcode(barcode: String): Single<BarcodeResponse> {
        return dataSource.getProductFromBarcode(barcode)
    }

}
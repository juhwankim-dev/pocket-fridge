package com.andback.pocketfridge.data.model

import com.google.gson.annotations.SerializedName

data class BarcodeResponse(
    @SerializedName("C005") val serviceName: ServiceName
)

data class ServiceName(
    @SerializedName("RESULT") val result: Result,
    @SerializedName("row") val data: List<ProductEntity>,
    @SerializedName("total_count") val totalCount: String
)

data class Result(
    @SerializedName("CODE") val code: String,
    @SerializedName("MSG") val msg: String
)


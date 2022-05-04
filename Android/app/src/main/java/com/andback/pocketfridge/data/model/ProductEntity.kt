package com.andback.pocketfridge.data.model

import com.google.gson.annotations.SerializedName

data class ProductEntity (
    @SerializedName("BAR_CD") val barcode: String,
    @SerializedName("BSSH_NM") val manufacName: String,
    @SerializedName("CLSBIZ_DT") val closingBizDay: String,
    @SerializedName("END_DT") val ProduceEndDay: String,
    @SerializedName("INDUTY_NM") val industryType: String,
    @SerializedName("POG_DAYCNT") val shelfLife: String,
    @SerializedName("PRDLST_DCNM") val type: String,
    @SerializedName("PRDLST_NM") val name: String,
    @SerializedName("PRDLST_REPORT_NO") val reportNo: String,
    @SerializedName("PRMS_DT") val reportDay: String,
    @SerializedName("SITE_ADDR") val siteAddr: String
)
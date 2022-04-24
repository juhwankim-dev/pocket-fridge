package com.andback.pocketfridge.domain.model

data class CheckResult(
    var errorMsg: String = "",
    var isValid: Boolean = false,
)

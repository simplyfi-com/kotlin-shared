package com.simplyfi.sdk.models.core.routines

import kotlinx.serialization.SerialName

enum class ParamType {
    @SerialName("S")
    String,
    @SerialName("I")
    Integer,
    @SerialName("N")
    Numeric
}
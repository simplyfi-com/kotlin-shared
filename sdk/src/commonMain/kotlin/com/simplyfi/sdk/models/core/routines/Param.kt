package com.simplyfi.sdk.models.core.routines

data class Param(
    val seq: Int,
    val name: String,
    val type: ParamType,
    val required: Boolean,
    val defaultValue: String,
    val description: String
)

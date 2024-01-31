package com.simplyfi.sdk.models.core.routines

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
data class Param(
    val seq: Int,
    val name: String,
    val type: ParamType,
    val required: Boolean,
    val defaultValue: String,
    @ObjCName("description") val description: String
)

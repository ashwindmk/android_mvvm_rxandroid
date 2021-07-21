package com.ashwin.mvvmrxandroid.domain.model

import com.google.gson.annotations.SerializedName

data class Color(
    val id: Long,
    val uid: String,
    @SerializedName("color_name") val colorName: String,
    @SerializedName("hex_value") val hexValue: String
)

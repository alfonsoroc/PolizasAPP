package com.example.polizasapp.data.TokenData

import com.google.gson.annotations.SerializedName

data class MetaToken(
    @SerializedName("status") val status: String,
    @SerializedName("count") val count: Int
)

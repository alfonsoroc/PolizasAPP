package com.example.polizasapp.uimodel

import com.example.polizasapp.data.TokenData.DataToken
import com.example.polizasapp.data.TokenData.MetaToken
import com.google.gson.annotations.SerializedName

data class ResponseToken(
    @SerializedName("meta") val meta: MetaToken,
    @SerializedName("data") var data: DataToken
)

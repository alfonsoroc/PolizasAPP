package com.example.polizasapp.uimodel

import com.example.polizasapp.data.PolizaData.Data
import com.example.polizasapp.data.PolizaData.Meta

data class PolizasResponse(
    val meta: Meta,
    val data: ArrayList<Data>

)

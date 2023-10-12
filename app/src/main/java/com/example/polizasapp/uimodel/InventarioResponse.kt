package com.example.polizasapp.uimodel

import com.example.polizasapp.data.InventarioData.DataInventario
import com.example.polizasapp.data.InventarioData.MetaInventario
import com.example.polizasapp.data.PolizaData.Data
import com.google.gson.annotations.SerializedName

data class InventarioResponse(
    @SerializedName("meta") val meta: MetaInventario,
    @SerializedName("data") val data: ArrayList<DataInventario>
)

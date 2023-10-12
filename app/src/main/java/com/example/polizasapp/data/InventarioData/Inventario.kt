package com.example.polizasapp.data.InventarioData

import com.google.gson.annotations.SerializedName

data class Inventario(
    @SerializedName("codigo") val codigo: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cantidad") val cantidad: Float,
    @SerializedName("familia") val familia: String,
    @SerializedName("precio") val precio: Float
)

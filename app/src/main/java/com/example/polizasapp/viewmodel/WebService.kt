package com.example.polizasapp.viewmodel

import com.example.polizasapp.data.TokenData.BodyToken
import com.example.polizasapp.uimodel.InventarioResponse
import com.example.polizasapp.uimodel.ModificaResponse
import com.example.polizasapp.uimodel.PolizasResponse
import com.example.polizasapp.uimodel.ResponseToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WebService {
    @GET("/polizas/Guardar")
    suspend fun getGuardar(
        @Query("cantidad") cantidad: Float,
        @Query("empleado") empleado:Int,
        @Query("sku") sku:Int
    ):Response<Any>

    @GET("/polizas/buscar")
    suspend fun  getPolizas(
        @Query("folio") folio: Int,
        @Query("empleado") empleado:Int
    ): Response<Any>

    @GET("/polizas/Actualizar")
    suspend fun getUpdate(
        @Query("poliza") poliza: Int,
        @Query("cantidad") cantidad: Float,
        @Query("sku") sku:Int
    ):Response<Any>

    @GET("/polizas/eliminar")
    suspend fun getEliminar(
        @Query("poliza") poliza: Int
    ):Response<ModificaResponse>

    @GET("/polizas/inventario")
    suspend fun getInventario(
        @Query("sku") sku:Int
    ):Response<Any>

    @POST("/sso-dev/api/v1/app/authenticate")
    @Headers("Content-Type: application/json")
    fun obtenerToken(@Body body: BodyToken): Call<ResponseToken>


}
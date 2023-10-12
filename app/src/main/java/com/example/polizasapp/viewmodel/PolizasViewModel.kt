package com.example.polizasapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polizasapp.core.Constantes
import com.example.polizasapp.core.Prefs
import com.example.polizasapp.core.Retrofit
import com.example.polizasapp.data.InventarioData.DataInventario
import com.example.polizasapp.data.Parametros
import com.example.polizasapp.data.PolizaData.Data
import com.example.polizasapp.data.TokenData.BodyToken
import com.example.polizasapp.uimodel.InventarioResponse
import com.example.polizasapp.uimodel.ModificaResponse
import com.example.polizasapp.uimodel.PolizasResponse
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class PolizasViewModel @Inject constructor(
    private val prefs: Prefs
) : ViewModel() {


    var poliza: Int = 0
    var sku: Int = 0
    var empleado: Int = 0
    var cantidad: Float = 0f

    val isLoading = MutableStateFlow(false)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _showDialogUpdate = MutableLiveData<Boolean>()
    val showDialogUpdate: LiveData<Boolean> = _showDialogUpdate


    private val _showDialogDelete = MutableLiveData<Boolean>()
    val showDialogDelete: LiveData<Boolean> = _showDialogDelete


    private val _polizaData = mutableStateListOf<Data>()
    val polizaData: List<Data> = _polizaData

    private val _inventarioData = mutableStateListOf<DataInventario>()
    val inventarioData: List<DataInventario> = _inventarioData

    private val _updateData = mutableStateListOf<Data>()
    val updateData: List<Data> = _updateData

    private val _showDialogError = MutableLiveData<Boolean>()

    val showDialogError: LiveData<Boolean> = _showDialogError

    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError


    fun onDialogError() {
        _showDialogError.value = true
    }

    fun onDialogErrorClose() {
        _mensajeError.value = ""
        _showDialogError.value = false
    }


    fun onDialogClose() {
        _showDialog.value = false
        _showDialogUpdate.value = false
    }


    fun onShowDialogCreate() {
        _showDialog.value = true
    }

    fun onShowDialogUpdate(data: Data) {
        _updateData.add(data)
        _showDialogUpdate.value = true
    }

    fun onShowDialogDelete(data: Data) {
        _updateData.add(data)
        _showDialogDelete.value = true
    }

    fun dialogDeleteClose() {
        _showDialogDelete.value = false
    }


    fun updatePolizaData(newData: List<Data>) {
        _polizaData.clear()
        _polizaData.addAll(newData)
    }

    fun onCreatePolizas(it: ArrayList<Parametros>) {
        _showDialog.value = false
        val gson = Gson()
        for (poliza in it) {
            sku = poliza.sku
            empleado = poliza.empleado
            cantidad = poliza.cantidad


        }
        viewModelScope.launch {
            val tokenDeferred = async { obtenerToken() }
            Constantes.token = tokenDeferred.await()
            val response = Retrofit.createWebServicePolizas(Constantes.token)
                .getGuardar(cantidad, empleado, sku)

            val jsonResponse = gson.toJson(response.body())
            val code = gson.toJson(response.code())
            withContext(Dispatchers.IO) {
                when (detectResponseType(jsonResponse, code)) {
                    ResponseType.SUCCES_WITH_DATA -> {
                        val responseService =
                            gson.fromJson(jsonResponse, PolizasResponse::class.java)
                        if (response.isSuccessful && responseService.meta.status == "ok") {
                            onSearchPolizas(0, 0)
                        } else {
                            throw IllegalArgumentException("${responseService.meta.status}")
                        }

                    }

                    ResponseType.ERROR -> {
                        if (response.isSuccessful) {
                            _mensajeError.value = ""
                            val responseError =
                                gson.fromJson(jsonResponse, ModificaResponse::class.java)
                            var dataError = responseError.data.Mensaje
                            _mensajeError.value = dataError

                        }

                    }

                    ResponseType.UNKNOWN -> {
                        _mensajeError.value = "Error al crear Polizas"
                        isLoading.value = false

                    }

                    ResponseType.INVALIDTOKEN -> {

                    }
                }
            }

        }
    }


    fun onSearchPolizas(poliza: Int, empleado: Int) {
        val gson = Gson()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withTimeout(20000) {
                    isLoading.value = poliza <= 0
                    val tokenDeferred = async { obtenerToken() }
                    Constantes.token = tokenDeferred.await()

                    val response =
                        Retrofit.createWebServicePolizas(Constantes.token)
                            .getPolizas(poliza, empleado)
                    val jsonResponse = gson.toJson(response.body())
                    val code = gson.toJson(response.code())

                    withContext(Dispatchers.IO) {
                        when (detectResponseType(jsonResponse, code)) {
                            ResponseType.SUCCES_WITH_DATA -> {
                                if (response.isSuccessful) {
                                    val responseObject =
                                        gson.fromJson(jsonResponse, PolizasResponse::class.java)
                                    var data: ArrayList<Data> = responseObject.data
                                    if (poliza > 0) {
                                        for (dt in data) {
                                            val index =
                                                _polizaData.indexOfFirst { it.poliza.idpoliza == dt.poliza.idpoliza }
                                            _polizaData[index] = dt
                                            isLoading.value = false
                                        }
                                    } else {

                                        val updatePolizaData: List<Data> = responseObject.data
                                        if (updatePolizaData.isNotEmpty()) {
                                            isLoading.value = false
                                        }
                                        updatePolizaData(updatePolizaData)

                                    }

                                } else {
                                    throw IllegalArgumentException("fallo la consulta a polizas")
                                }


                            }

                            ResponseType.ERROR -> {
                                _mensajeError.value = ""
                                val responseError =
                                    gson.fromJson(jsonResponse, ModificaResponse::class.java)

                                var dataError = responseError.data.Mensaje
                                _mensajeError.value = dataError
                                isLoading.value = false

                            }

                            ResponseType.UNKNOWN -> {
                                _mensajeError.value = "Error al consultar Polizas"
                                isLoading.value = false
                            }

                            ResponseType.INVALIDTOKEN -> {
                                validarToken()
                            }

                        }

                    }
                }

            } catch (e: TimeoutCancellationException) {
                _mensajeError.value = "Error al Consultar Polizas"
                isLoading.value = false
            }
        }
    }


    fun updateInventarioData(newData: List<DataInventario>) {
        _inventarioData.clear()
        _inventarioData.addAll(newData)

    }


    fun getInventario(sku: Int) {
        val gson = Gson()
        viewModelScope.launch(Dispatchers.IO) {
            val tokenDeferred = async { obtenerToken() }
            Constantes.token = tokenDeferred.await()
            isLoading.value = true
            val response = Retrofit.createWebServicePolizas(Constantes.token).getInventario(sku)
            val jsonResponse = gson.toJson(response.body())
            val code = gson.toJson(response.code())
            withContext(Dispatchers.IO) {
                when (detectResponseType(jsonResponse, code)) {
                    ResponseType.SUCCES_WITH_DATA -> {
                        if (response.isSuccessful) {
                            val responseData =
                                gson.fromJson(jsonResponse, InventarioResponse::class.java)
                            val dataInventario: List<DataInventario> = responseData.data
                            if (dataInventario.isNotEmpty()) {
                                isLoading.value = false
                            }
                            updateInventarioData(dataInventario)
                        }
                    }

                    ResponseType.ERROR -> {
                        _mensajeError.value = "Error al consultar SKU"
                        isLoading.value = false
                    }

                    ResponseType.UNKNOWN -> {
                        _mensajeError.value = "Error al consultar SKU"
                        isLoading.value = false
                    }

                    ResponseType.INVALIDTOKEN -> {
                        validarToken()

                    }
                }
            }
        }
    }

    fun updatePoliza(it: ArrayList<Parametros>) {
        _showDialogUpdate.value = false
        val gson = Gson()
        for (parametros in it) {
            poliza = parametros.poliza
            sku = parametros.sku
            cantidad = parametros.cantidad
        }

        viewModelScope.launch(Dispatchers.IO) {

            val tokenDeferred = async { obtenerToken() }
            Constantes.token = tokenDeferred.await()
            val response =
                Retrofit.createWebServicePolizas(Constantes.token).getUpdate(poliza, cantidad, sku)

            val jsonResponse = gson.toJson(response.body())
            val responseService =
                gson.fromJson(jsonResponse, ModificaResponse::class.java)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful && responseService.meta.status == "ok") {
                    onSearchPolizas(poliza, 0)
                } else if (responseService.meta.status == "FAILURE") {
                    var dataError = responseService.data.Mensaje
                    _mensajeError.value = dataError
                } else {
                    throw IllegalArgumentException("Fallo Consulta al servicio")
                }
            }
        }


    }


    fun deletePoliza(it: ArrayList<Parametros>) {
        _showDialogDelete.value = false
        for (parametros in it) {
            poliza = parametros.poliza
        }
        viewModelScope.launch(Dispatchers.IO) {
            //val response = Retrofit.consultarServicePolizas.getEliminar(poliza)
            val tokenDeferred = async { obtenerToken() }
            Constantes.token = tokenDeferred.await()
            val response = Retrofit.createWebServicePolizas(Constantes.token).getEliminar(poliza)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful && response.body()!!.meta.status == "ok") {
                    val index = _polizaData.indexOfFirst { it.poliza.idpoliza == poliza }
                    _polizaData.remove(_polizaData[index])

                } else if (response.body()!!.meta.status == "FAILURE") {
                    var dataError = response.body()!!.meta.status
                    _mensajeError.value = dataError
                }


            }
        }
    }


    enum class ResponseType {
        ERROR,
        SUCCES_WITH_DATA,
        UNKNOWN,
        INVALIDTOKEN
    }


    fun detectResponseType(jsonResponse: String, code: String): ResponseType {
        val jsonElement: JsonElement = JsonParser.parseString(jsonResponse)

        if (jsonElement.isJsonObject) {
            val jsonObject = jsonElement.asJsonObject
            if (jsonObject.isJsonObject) {

                val dataElement = jsonObject.asJsonObject.get("data")
                println(dataElement)
                if (dataElement.isJsonObject && dataElement.asJsonObject.has("Mensaje:")) {
                    return ResponseType.ERROR
                } else if (dataElement.isJsonArray && dataElement.asJsonArray.size() > 0) {

                    return ResponseType.SUCCES_WITH_DATA
                }
            }
        }
        if (code == "406") {
            return ResponseType.INVALIDTOKEN
        }
        return ResponseType.UNKNOWN

    }

    fun obtenerToken(): String {
        if (Constantes.token.isEmpty()) {
            try {
                val apiService = Retrofit.webServiceToken


                val serviceTokenResponse = apiService.obtenerToken(
                    BodyToken(
                        appId = Constantes.appId,
                        appKey = Constantes.appKey
                    )
                )
                val response = serviceTokenResponse.execute()
                if (response.isSuccessful) {
                    prefs.saveToken(response.body()!!.data.token)
                    Constantes.token = response.body()!!.data.token
                    response.body() ?: throw NullPointerException("Respuesta nula ")

                } else {
                    throw Exception("Error en la peticion")

                }
            } catch (e: Exception) {
                Log.d("error", "${e}")
                throw IllegalArgumentException("fallo conexion")
            }
        }
        return Constantes.token
    }

    fun validarToken() {
        _mensajeError.value = "Token Invalido"
        isLoading.value = false
        Constantes.token = ""
    }


}



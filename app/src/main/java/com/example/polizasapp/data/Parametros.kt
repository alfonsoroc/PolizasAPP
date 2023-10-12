package com.example.polizasapp.data

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import  java.util.Date

data class Parametros(
    var empleado:Int,
    var sku:Int,
    var cantidad:Float,
    var poliza:Int,
    var fecha:String = "2023-06-25"




)

package com.example.polizasapp.uimodel

sealed class Routes(val route:String) {
    object InicioScreen:Routes("InicioScreenApp")
    object PolizaScreen:Routes("ScreenPolizas")
    object InventarioScreen:Routes("ScaffoldScreen")


}

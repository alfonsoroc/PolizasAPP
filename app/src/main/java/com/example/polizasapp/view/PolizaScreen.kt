package com.example.polizasapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.polizasapp.data.Parametros
import com.example.polizasapp.data.PolizaData.Data
import com.example.polizasapp.uimodel.Routes
import com.example.polizasapp.viewmodel.PolizasViewModel


val coorSteelBlue = Color(0xFF4682B4)
val colorAliceBlue = Color(0xFFF0F8FF)
val colorSkyBlue = Color(0xFF87CEEB)


@Composable
fun ScreenPolizas(polizasViewModel: PolizasViewModel, navigationController: NavHostController) {
    Scaffold(

        topBar = { TopAppBArPolizas(polizasViewModel) },
        bottomBar = { BarraNavigation(polizasViewModel, navigationController) }

    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {

            InicioScreen(polizasViewModel,navigationController)
        }

    }
}


@Composable
fun TopAppBArPolizas(polizasViewModel: PolizasViewModel) {
    val foscuManager = LocalFocusManager.current

    var empleado by remember { mutableStateOf("") }
    Column(modifier = Modifier.background(colorSkyBlue)) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Polizas Faltantes de Inventario",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontStyle = FontStyle.Normal,

                        )
                },

                backgroundColor = coorSteelBlue,
                contentColor = Color.White,
                elevation = 8.dp,
            )
        }
        Row() {
            Text(
                text = "Usuario: Alfonso Rocha",
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp)
            )

        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = empleado,
                onValueChange = { empleado = it },

                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 7.dp)
                    .weight(1f)
                    .height(55.dp),

                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.textFieldColors(
                    Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color.White
                ),

                shape = RoundedCornerShape(10.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                label = {
                    Text(
                        "#Empleado",
                        modifier = Modifier.height(15.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp

                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            IconButton(onClick = {
                polizasViewModel.onSearchPolizas(0, empleado.toInt())
                empleado = ""
                foscuManager.clearFocus()
            }

            ) {

                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
        }

    }
}


@Composable
fun BarraNavigation(polizasViewModel: PolizasViewModel, navigationController: NavHostController) {
    BottomNavigation(backgroundColor = coorSteelBlue) {
        BottomNavigationItem(selected = false,
            onClick = { navigationController.navigate(Routes.InicioScreen.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)

                )
            },
            label = { Text(text = "Inicio", color = Color.White) })



        BottomNavigationItem(selected = false, onClick = {
            navigationController.navigate(Routes.InventarioScreen.route)
            polizasViewModel.getInventario(0)
        },
            icon = {
                Icon(
                    Icons.Filled.Store,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }, label = { Text(text = "Inventario", color = Color.White) }

        )
        BottomNavigationItem(selected = false, onClick = { polizasViewModel.onShowDialogCreate() },
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }, label = { Text(text = "ADD", color = Color.White) })


    }

}


@Composable
fun InicioScreen(polizasViewModel: PolizasViewModel, navigationController: NavHostController) {

    val showDialog: Boolean by polizasViewModel.showDialog.observeAsState(false)
    val showDialogUpdate: Boolean by polizasViewModel.showDialogUpdate.observeAsState(false)
    val showDialogDelete: Boolean by polizasViewModel.showDialogDelete.observeAsState(false)
    val updateData: List<Data> = polizasViewModel.updateData
    val showDialogError: Boolean by polizasViewModel.showDialogError.observeAsState(false)
    val mensajeError: String by polizasViewModel.mensajeError.collectAsState("")

    val isLoading by polizasViewModel.isLoading.collectAsState()

    if (isLoading){
        ProgressConsulta()
    }else {

        if (mensajeError != "") {
            polizasViewModel.onDialogError()
        }



        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            AddPolizasDialog(
                showDialog,
                onDismiss = { polizasViewModel.onDialogClose() },
                onPolizaAdd = { polizasViewModel.onCreatePolizas(it) })

            UpdatePolizasDialog(
                updateData,
                showDialogUpdate,
                onDismiss = { polizasViewModel.onDialogClose() }
            ) { polizasViewModel.updatePoliza(it) }

            AlertDialogDelete(
                updateData,
                show = showDialogDelete,
                onDismiss = { polizasViewModel.dialogDeleteClose() },
                onCorfim = { polizasViewModel.deletePoliza(it) })

            AlertDialogError(
                navigationController,
                show = showDialogError,
                onDismiss = { polizasViewModel.onDialogErrorClose() },
                mensajeError = mensajeError
            )



            PolizasList(polizasViewModel)
        }
    }
}

@Composable
fun ProgressConsulta(){
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}


@Composable
fun PolizasList(polizasViewModel: PolizasViewModel) {
    val polizaData: List<Data> = polizasViewModel.polizaData

    LazyColumn {
        items(polizaData, key = { it.poliza.idpoliza }) { poliza ->
            ItemPolizas(poliza, polizasViewModel)
        }
    }
}


@Composable
fun ItemPolizas(polizasModel: Data, polizasViewModel: PolizasViewModel) {

    Card(
        Modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = colorAliceBlue
        )

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp, vertical = 8.dp)

        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()

            )
            {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {

                    TextCardPolizas("Poliza:",polizasModel.poliza.idpoliza.toString())

                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    Text(
                        text = "Empleado: ${polizasModel.empleado.nombre} ${polizasModel.empleado.apellido}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )


                }

                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    TextCardPolizas("SKU:", polizasModel.detalleArticulo.sku.toString() )

                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    TextCardPolizas("Articulo:", polizasModel.detalleArticulo.nombre)

                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 14.dp)
                ) {
                    TextCardPolizas("Cantidad:", polizasModel.poliza.cantidad.toInt().toString())


                }

            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()


            ) {
                IconButton(
                    onClick = { polizasViewModel.onShowDialogDelete(polizasModel) },
                    modifier = Modifier


                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { polizasViewModel.onShowDialogUpdate(polizasModel) },
                    modifier = Modifier


                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color.Black
                    )
                }


            }


        }

    }


}




@Composable
fun TextCardPolizas(parametro:String,valor:String){
        Text(text = parametro, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = valor,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

}









fun createParams(
    empleado: String,
    sku: String,
    cantidad: String,
    poliza: String
): ArrayList<Parametros> {
    var listParam: ArrayList<Parametros> by mutableStateOf(arrayListOf())
    var params = Parametros(empleado.toInt(), sku.toInt(), cantidad.toFloat(), poliza.toInt())

    listParam.add(params)


    return listParam
}

package com.example.polizasapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.polizasapp.data.InventarioData.DataInventario
import com.example.polizasapp.uimodel.Routes
import com.example.polizasapp.viewmodel.PolizasViewModel

val ColorCard = Color(0xFF1E90FF)
@Composable
fun ScaffoldScreen(
    polizasViewModel: PolizasViewModel, navigationController: NavHostController
) {
    Scaffold(topBar = { ApptoBarInventario(polizasViewModel) },
        bottomBar = { BarraNavecegacion(navigationController) }

    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            ScreenInventario(polizasViewModel,navigationController)

        }

    }

}


@Composable
fun ApptoBarInventario(polizasViewModel: PolizasViewModel) {
    var sku by remember { mutableStateOf("") }
    val foscuManager = LocalFocusManager.current

    Column(modifier = Modifier.background(colorSkyBlue)) {
        Row(
            modifier = Modifier, horizontalArrangement = Arrangement.Center
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Inventario",
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
                value = sku,
                onValueChange = { sku = it },

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
                textStyle = TextStyle(
                    fontSize = 18.sp, color = Color.Black
                ),
                label = {
                    Text(
                        "SKU",
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
                polizasViewModel.getInventario(sku.toInt())
                sku = ""
                foscuManager.clearFocus()
            }) {

                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
        }

    }
}

@Composable
fun BarraNavecegacion(navigationController: NavHostController) {
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
        BottomNavigationItem(selected = false,
            onClick = { navigationController.navigate(Routes.PolizaScreen.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.FileOpen,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)

                )
            },
            label = { Text(text = "Polizas", color = Color.White) })


    }
}

@Composable
fun ScreenInventario(polizasViewModel: PolizasViewModel,navigationController: NavHostController) {
    val mensajeError: String by polizasViewModel.mensajeError.collectAsState("")
    val showDialogError: Boolean by polizasViewModel.showDialogError.observeAsState(false)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (mensajeError != "") {
            polizasViewModel.onDialogError()
        }
        InventarioList(polizasViewModel)
        AlertDialogError(
            navigationController,
            show = showDialogError,
            onDismiss = { polizasViewModel.onDialogErrorClose() },
            mensajeError = mensajeError
        )

    }

}

@Composable
fun InventarioList(polizasViewModel: PolizasViewModel) {
    val inventarioData: List<DataInventario> = polizasViewModel.inventarioData

    val isLoadingInventario by polizasViewModel.isLoading.collectAsState()

    if (isLoadingInventario) {
        ProgressConsulta()
    } else
    {

        LazyColumn {
            if (inventarioData.isNotEmpty()) {
                items(inventarioData, key = { it.codigo }) { inventario ->
                    ItemInventario(inventario)
                }
            }

        }
    }
}


@Composable
fun ItemInventario(inventario: DataInventario) {
    Column(Modifier.height(150.dp)) {
        CardInventario(inventario)
        FamiliaInventario(inventario)

    }
}

@Composable
fun CardInventario(inventario: DataInventario) {
    Card(
        Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .height(100.dp),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Column(
            Modifier.fillMaxHeight(),
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp, vertical = 8.dp)


            ) {
                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {

                    RowsCard(parametro = "Articulo", valor = inventario.nombre)
                    SpaceRows()
                    RowsCard(parametro = "Precio", valor = inventario.precio.toInt().toString())

                }
                Column() {
                    RowsCard(parametro = "Codigo", valor = inventario.codigo.toInt().toString())
                    SpaceRows()
                    RowsCard(
                        parametro = "Cantidad", valor = inventario.cantidad.toInt().toString()
                    )
                }
            }

        }

    }

}

@Composable
fun FamiliaInventario(inventario: DataInventario) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
            .background(colorSkyBlue)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RectangleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = inventario.familia,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun RowsCard(parametro: String, valor: String) {

    Row(
        Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(text = "${parametro}:", fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = valor, fontWeight = FontWeight.Bold, color = Color.Black
        )

    }


}

@Composable
fun SpaceRows() {
    Spacer(modifier = Modifier.height(2.dp))
}


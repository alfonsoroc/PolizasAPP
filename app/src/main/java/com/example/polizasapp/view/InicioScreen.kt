package com.example.polizasapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.polizasapp.R
import com.example.polizasapp.uimodel.Routes
import com.example.polizasapp.viewmodel.PolizasViewModel

val WhiteSmoke= Color(0xFFF5F5F5)


@Composable
fun InicioScreenApp(navigationController: NavHostController, polizasViewModel: PolizasViewModel) {
    Scaffold(topBar = { AppBarScreen() }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),


            ) {
            Box(
                modifier = Modifier.fillMaxSize().background(WhiteSmoke),

            ) {
                ScreenOpciones(navigationController,polizasViewModel)
            }

        }

    }

}

@Composable
fun AppBarScreen() {
    Column() {
        Row(
            modifier = Modifier, horizontalArrangement = Arrangement.Center
        ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Bienvenido",
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

        Row(
            modifier = Modifier
                .border( width = 1.dp,
                    color = Color.LightGray,
                    shape = RectangleShape),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Seleccione el tipo de accion que quiere realizar",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontStyle = FontStyle.Normal,

                )
        }

    }

}


@Composable
fun ScreenOpciones(navigationController: NavHostController, polizasViewModel: PolizasViewModel) {
    Column(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column() {
            Card(
                modifier = Modifier
                    .clickable {
                        navigationController.navigate(Routes.PolizaScreen.route)
                        polizasViewModel.onSearchPolizas(0,0)
                    }
                    .height(150.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(
                    containerColor = colorAliceBlue
                )


            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nota),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )

                }


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RectangleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Polizas", fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontStyle = FontStyle.Normal,
                )

            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column() {
            Card(
                modifier = Modifier
                    .clickable {
                        navigationController.navigate(Routes.InventarioScreen.route)
                        polizasViewModel.getInventario(0)
                    }
                    .height(150.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(
                    containerColor = colorAliceBlue
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.inventario),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RectangleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Inventario", fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                fontStyle = FontStyle.Normal,
            )

        }


    }

}
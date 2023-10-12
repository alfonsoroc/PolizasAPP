package com.example.polizasapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.polizasapp.data.Parametros
import com.example.polizasapp.data.PolizaData.Data
import com.example.polizasapp.uimodel.Routes

@Composable
fun AddPolizasDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onPolizaAdd: (ArrayList<Parametros>) -> Unit
) {

    var empleado by remember { mutableStateOf("") }
    var sku by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }



    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            )
            {
                Text(
                    text = "Agregar Polizas",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = empleado,
                    onValueChange = { empleado = it },
                    label = { Text("Empleado") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = sku,
                    onValueChange = { sku = it },
                    label = { Text("SKU") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )

                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))


                Button(onClick = {
                    onPolizaAdd(createParams(empleado, sku, cantidad, "0"))
                    empleado = ""
                    cantidad = ""
                    sku = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir Poliza")
                }

            }

        }
    }

}

@Composable
fun UpdatePolizasDialog(
    updateData: List<Data>,
    show: Boolean,
    onDismiss: () -> Unit,
    onPolizaUpdate: (ArrayList<Parametros>) -> Unit
) {
    var dtpoliza = ""
    var dtsku = ""
    var dtcantidad = ""



    for (dt in updateData) {
        dtpoliza = dt.poliza.idpoliza.toString()
        dtsku = dt.detalleArticulo.sku.toString()
        dtcantidad = dt.poliza.cantidad.toInt().toString()
    }


    var sku by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    sku = dtsku
    cantidad = dtcantidad

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            )
            {
                Text(
                    text = "Modificar Poliza",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.size(16.dp))

                TextField(
                    value = dtpoliza,
                    onValueChange = { dtpoliza = it },
                    label = { Text("Poliza") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    enabled = false
                )

                Spacer(modifier = Modifier.size(16.dp))

                TextField(
                    value = sku,
                    onValueChange = { sku = it },
                    label = { Text("SKU") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )

                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))


                Button(onClick = {
                    onPolizaUpdate(createParams("0", sku, cantidad, dtpoliza))

                    cantidad = ""
                    sku = ""
                    dtpoliza = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Actualizar Poliza")
                }

            }

        }
    }

}

@Composable
fun AlertDialogDelete(
    updateData: List<Data>,
    show: Boolean,
    onDismiss: () -> Unit,
    onCorfim: (ArrayList<Parametros>) -> Unit
) {
    var dtpoliza = ""



    for (dt in updateData) {
        dtpoliza = dt.poliza.idpoliza.toString()

    }
    if (show) {
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = "") },
            text = { Text(text = "Seguro de eliminar la poliza") },
            confirmButton = {
                TextButton(onClick = { onCorfim(createParams("0", "0", "0", dtpoliza)) }) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

}

@Composable
fun AlertDialogError(
    navigationController: NavHostController,
    show: Boolean,
    onDismiss: () -> Unit,
    mensajeError: String
) {
    if (show) {
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = "") },
            text = { Text(text = mensajeError) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        navigationController.navigate(Routes.InicioScreen.route)
                    }) {
                    Text(text = "Confirmar")
                }
            }

        )


    }
}
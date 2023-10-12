package com.example.polizasapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.polizasapp.ui.theme.PolizasAPPTheme
import com.example.polizasapp.uimodel.Routes
import com.example.polizasapp.viewmodel.PolizasViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


class MainActivity : ComponentActivity() {

    private val polizasViewModel: PolizasViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolizasAPPTheme {
                Surface(

                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.InicioScreen.route
                    ) {
                        composable(Routes.InicioScreen.route){
                            InicioScreenApp(
                                navigationController,
                                polizasViewModel
                            )
                        }


                        composable(Routes.PolizaScreen.route) {
                            ScreenPolizas(
                                polizasViewModel,
                                navigationController
                            )
                        }
                        composable(Routes.InventarioScreen.route) {
                            ScaffoldScreen(
                                polizasViewModel,
                                navigationController
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PolizasAPPTheme {
        Greeting("Android")
    }
}
package com.example.quijoteclubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quijoteclubapp.Aficionado.VentanaAficionado
import com.example.quijoteclubapp.DatosUsuario.DatosUsuarioViewModel
import com.example.quijoteclubapp.Login.LoginScreen
import com.example.quijoteclubapp.Login.LoginViewModel
import com.example.quijoteclubapp.PadreMadre.VentanaPadreMadre
import com.example.quijoteclubapp.ui.theme.QuijoteClubAppTheme

class MainActivity : ComponentActivity() {
    val loginVM = LoginViewModel()
    val datosUsuariVM = DatosUsuarioViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val contexto = LocalContext.current

            QuijoteClubAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Rutas.login) {
                    composable(Rutas.login) {
                        LoginScreen(navController, loginVM, datosUsuariVM)
                    }
                    composable(Rutas.aficionado){
                        VentanaAficionado()
                    }
                    composable(Rutas.padreMadre){
                        VentanaPadreMadre()
                    }
                }

            }
        }
    }
}



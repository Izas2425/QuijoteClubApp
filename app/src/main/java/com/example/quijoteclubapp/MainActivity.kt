package com.example.quijoteclubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quijoteclubapp.AdminSettings.AdminSettingsViewModel
import com.example.quijoteclubapp.Administrador.Entrenadores.VentanaEntrenadores
import com.example.quijoteclubapp.Administrador.Eventos.VentanaEventos
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaJugadores
import com.example.quijoteclubapp.Administrador.VentanaAdministrador
import com.example.quijoteclubapp.Aficionado.VentanaAficionado
import com.example.quijoteclubapp.DatosUsuario.DatosUsuarioViewModel
import com.example.quijoteclubapp.Login.LoginScreen
import com.example.quijoteclubapp.Login.LoginViewModel
import com.example.quijoteclubapp.PadreMadre.PadresMadresViewModel
import com.example.quijoteclubapp.PadreMadre.VentanaPadreMadre
import com.example.quijoteclubapp.PadreMadre.VentanaRegistrarse
import com.example.quijoteclubapp.ui.theme.QuijoteClubAppTheme

class MainActivity : ComponentActivity() {
    val loginVM = LoginViewModel()
    val datosUsuariVM = DatosUsuarioViewModel()
    val padresMadresVM = PadresMadresViewModel ()
    val adminSettingsVM = AdminSettingsViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val contexto = LocalContext.current

            QuijoteClubAppTheme {
                val navController = rememberNavController()
//                NavHost(navController = navController, startDestination = Rutas.registrarse){
                NavHost(navController = navController, startDestination = Rutas.login) {
                    composable(Rutas.login) {
                        LoginScreen(navController, loginVM, datosUsuariVM, adminSettingsVM)
                    }
                    composable(Rutas.aficionado){
                        VentanaAficionado()
                    }
                    composable(Rutas.padreMadre){
                        VentanaPadreMadre()
                    }
                    composable(Rutas.admin){
                        VentanaAdministrador(navController)
                    }
                    composable(Rutas.registrarse) {
                        VentanaRegistrarse(navController,loginVM,padresMadresVM,contexto)
                    }
                    composable(Rutas.partidos){
                        VentanaJugadores()
                    }
                    composable(Rutas.jugadores){
                        VentanaJugadores()
                    }
                    composable(Rutas.entrenadores){
                        VentanaEntrenadores()
                    }
                    composable(Rutas.eventos){
                        VentanaEventos()
                    }
                }

            }
        }
    }
}



package com.example.quijoteclubapp.Administrador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.quijoteclubapp.Administrador.Entrenadores.VentanaEntrenadores
import com.example.quijoteclubapp.Administrador.Eventos.VentanaEventos
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaJugadores
import com.example.quijoteclubapp.Administrador.Partidos.VentanaPartidos
import com.example.quijoteclubapp.R
import kotlinx.coroutines.launch

import androidx.compose.material.icons.rounded.SportsBasketball
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.filled.RecordVoiceOver
import com.example.quijoteclubapp.Administrador.CategoriaEquipos.DatosCategoriaEquipoViewModel
import com.example.quijoteclubapp.Administrador.Jugadores.DatosJugadoresViewModel
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaAltaJugador
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaListadoJugadores
import com.example.quijoteclubapp.Login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaAdministrador(
    navController: NavHostController,
    datosJugadorVM: DatosJugadoresViewModel,
    loginVM: LoginViewModel,
    datosCategoriaEquiposVM: DatosCategoriaEquipoViewModel,

){

    // estado para controlar la apertura/cierre del menu hamburguesa
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val estadoVentana = remember { mutableStateOf("Partidos") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(180.dp)
            ) {
                //A partir de aquí opciones del menúy qué pasa al pinchar sobre cada una
                HorizontalDivider()
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.SportsBasketball, // 🏀 partidos
                            contentDescription = "Baloncesto",
                            tint = colorResource(R.color.texto)
                        )
                    },
                    label = { Text(
                        text = "Partidos",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = {
                        //navController.navigate(Rutas.partidos)
                        estadoVentana.value = "Partidos"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person, // jugadores
                            contentDescription = "Entrenador",
                            tint = colorResource(R.color.texto)
                        )
                    },
                    label = { Text(
                        text = "Jugadores",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = {
//                        navController.navigate(Rutas.jugadores)
                        estadoVentana.value = "Jugadores"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver, // 🗣️ entreadador
                            contentDescription = "Entrenadores",
                            tint = colorResource(R.color.texto)
                        )
                    },
                    label = { Text(
                        text = "Entrenadores",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = {
//                        navController.navigate(Rutas.entrenadores)
                        estadoVentana.value = "Entrenadores"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday, // 📅 calendario
                            contentDescription = "Eventos",
                            tint = colorResource(R.color.texto)
                        )
                    },
                    label = { Text(
                        text = "Eventos",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = {
//                        navController.navigate(Rutas.eventos)
                        estadoVentana.value = "Eventos"
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
    ){
// CONTENIDO PRINCIPAL CON BARRA SUPERIOR
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(
                        text = "Administrador",
                        color = colorResource(R.color.textoBotones)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                               // tint = colorResource((R.color.botones))
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(R.color.botones) // 🔵 color de fondo de la barra
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                when (estadoVentana.value){
                    "Partidos" -> {
                       VentanaPartidos()
                    }
                    "Jugadores" -> {
                        // para que cuando pinche en el botón de añadir jugador
                        // se cargue la ventana de alta jugador
                        VentanaJugadores(
                            enAddJugador = {
                                datosJugadorVM.iniciarJugadorAdded()
                                estadoVentana.value = "AltaJugador"
                            }, datosCategoriaEquiposVM, datosJugadorVM,
                            enJugadoresMostrados = { estadoVentana.value ="ListadoJugadores"}
                        )
                    }
                    "Entrenadores" -> {
                        VentanaEntrenadores()
                    }
                    "Eventos" -> {
                        VentanaEventos()
                    }
                    "AltaJugador"->{
                        // para que cuando le de a guardar en el alta del jugador
                        // vuelva a cargar la ventana de los jugadores
                        VentanaAltaJugador(datosJugadorVM, loginVM,
                            enJugadorGuardado = {estadoVentana.value = "Jugadores"}
                        )
                    }
                    "ListadoJugadores"->{
                        VentanaListadoJugadores(datosJugadorVM){
                            estadoVentana.value = "Jugadores"
                        }
                    }


                }
            }
        }
    }
}
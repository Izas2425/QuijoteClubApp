package com.example.quijoteclubapp.Aficionado

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.rounded.SportsBasketball
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
import androidx.compose.ui.unit.sp
import com.example.quijoteclubapp.Administrador.Entrenadores.VentanaEntrenadores
import com.example.quijoteclubapp.Administrador.Eventos.VentanaEventos
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaAltaJugador
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaJugadores
import com.example.quijoteclubapp.Administrador.Partidos.VentanaPartidos
import com.example.quijoteclubapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaAficionado(){
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
                        estadoVentana.value = "Próximos partidos"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = {
//                        Icon(
////                            imageVector = Icons.Default.Person, // jugadores
//                            contentDescription = "Categorias",
//                            tint = colorResource(R.color.texto)
//                        )
                    },
                    label = { Text(
                        text = "Categorias",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = {
//                        navController.navigate(Rutas.jugadores)
                        estadoVentana.value = "Categorias"
                        scope.launch { drawerState.close() }
                    }
                )
//                NavigationDrawerItem(
//                    icon = {
////                        Icon(
//////                            imageVector = Icons.Default.RecordVoiceOver, // 🗣️ entreadador
////                            contentDescription = "Entrenadores",
////                            tint = colorResource(R.color.texto)
////                        )
//                    },
//                    label = { Text(
//                        text = "Equipos",
//                        color = colorResource(R.color.texto)) },
//                    selected = false,
//                    onClick = {
////                        navController.navigate(Rutas.entrenadores)
//                        estadoVentana.value = "Equipos"
//                        scope.launch { drawerState.close() }
//                    }
//                )
                NavigationDrawerItem(
                    icon = {
//                        Icon(
//                            imageVector = Icons.Outlined.CalendarToday, // 📅 calendario
//                            contentDescription = "Eventos",
//                            tint = colorResource(R.color.texto)
//                        )
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
                        text = "Aficionado",
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
//                when (estadoVentana.value){
//                    "Partidos" -> {
//                        VentanaPartidos()
//                    }
//                    "Jugadores" -> {
//                        // para que cuando pinche en el botón de añadir jugador
//                        // se cargue la ventana de alta jugador
//                        VentanaJugadores(
////                            enAddJugador = {estadoVentana.value= "AltaJugador"}
//                            enAddJugador = {
//                                datosJugadorVM.iniciarJugadorAdded()
//                                estadoVentana.value = "AltaJugador"
//                            }
//                        )
//                    }
//                    "Entrenadores" -> {
//                        VentanaEntrenadores()
//                    }
//                    "Eventos" -> {
//                        VentanaEventos()
//                    }
//                    "AltaJugador"->{
//                        // para que cuando le de a guardar en el alta del jugador
//                        // vuelva a cargar la ventana de los jugadores
//                        VentanaAltaJugador(datosJugadorVM, loginVM,
//                            enJugadorGuardado = {estadoVentana.value = "Jugadores"}
//                        )
//                    }


//
            }
        }
    }
}
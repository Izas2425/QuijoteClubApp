package com.example.quijoteclubapp.Administrador

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quijoteclubapp.Administrador.Entrenadores.VentanaEntrenadores
import com.example.quijoteclubapp.Administrador.Eventos.VentanaEventos
import com.example.quijoteclubapp.Administrador.Jugadores.VentanaJugadores
import com.example.quijoteclubapp.Administrador.Partidos.VentanaPartidos
import com.example.quijoteclubapp.R
import com.example.quijoteclubapp.Rutas
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaAdministrador(navController: NavHostController){

    // estado para controlar la apertura/cierre del menu hamburguesa
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(180.dp)
            ) {
                //A partir de aquí opciones del menú.
                HorizontalDivider()
                NavigationDrawerItem(
                    icon = null,  // luego agrego iconos
                    label = { Text(
                        text = "Partidos",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = { navController.navigate(Rutas.partidos) }
                )
                NavigationDrawerItem(
                    icon = null, // luego pongo iconos
                    label = { Text(
                        text = "Jugadores",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = { navController.navigate(Rutas.jugadores)}
                )
                NavigationDrawerItem(
                    icon = null,
                    label = { Text(
                        text = "Entrenadores",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = { navController.navigate(Rutas.entrenadores) }
                )
                NavigationDrawerItem(
                    icon = null,
                    label = { Text(
                        text = "Eventos",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = { navController.navigate(Rutas.eventos) }
                )

                HorizontalDivider()
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Cerrar menú"
                        )},
                    label = { Text(
                        text = "Cerrar menú",
                        color = colorResource(R.color.texto)) },
                    selected = false,
                    onClick = { scope.launch {
                        drawerState.apply {
                            close()
                        }
                    }}
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
                Text(
                    text = "Bienvenido al panel de administrador",
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
    }

}
package com.example.quijoteclubapp.Entrenador

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quijoteclubapp.Login.LoginViewModel
import com.example.quijoteclubapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaEntrenador(loginVM: LoginViewModel, entrenadorVM: EntrenadorViewModel){

    val scope = rememberCoroutineScope()
    val pantallaActiva = remember { mutableStateOf("Inicio") }
    val equipo by entrenadorVM.equipo

    LaunchedEffect(Unit) {
        entrenadorVM.equiposDisponibles(entrenadorVM.email.value)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Entrenador",
                        color = colorResource(R.color.texto),
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(R.color.botones)
                )
            )
        }
    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//
        val modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)

        if (pantallaActiva.value == "Inicio") {
            Column(modifier = modifier) {

                Spacer(modifier = Modifier.height(70.dp))

                // Selección de equipo
                equipos_Entrenador(loginVM, entrenadorVM)

                val elegido = equipo.isNotEmpty()

                Spacer(modifier = Modifier.height(70.dp))

                // Botones de navegación dentro de la ventana
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { pantallaActiva.value = "Estadisticas" },
                        enabled = equipo.isNotEmpty(),
//                        enabled = elegido,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.botones),
                            contentColor = colorResource(R.color.textoBotones)
                        )
                    ) {
                        Text("📊 Estadísticas")
                    }

                    Button(
                        onClick = {
                            entrenadorVM.partidosDelEquipo(entrenadorVM.equipo.value)
                            pantallaActiva.value = "Convocatoria" },
                        enabled = elegido,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.botones),
                            contentColor = colorResource(R.color.textoBotones)
                        )
                    ) {
                        Text("📋 Sugerir convocatoria")
                    }
                }
            }

        }else {
                Spacer(modifier = Modifier.height(16.dp))

                // Contenido que cambia según la opción seleccionada
                when (pantallaActiva.value) {
                    "Estadisticas" -> {
                        VentanaEstadisticas(
                            entrenadorVM.equipo.value,
                            entrenadorVM,
                            onAceptar ={
                                entrenadorVM.limpiarEquipo()
                                pantallaActiva.value = "Inicio"
                            }
                            )

                        // Aquí iría tu pantalla de estadísticas
//                    Text("Aquí se mostrarán las estadísticas del equipo seleccionado.")
                        // VentanaEstadisticas(entrenadorVM.equipo.value)
                    }

                    "Convocatoria" -> {
                        // Aquí iría tu pantalla de convocatoria sugerida
//                        Text("Aquí se mostrará la convocatoria sugerida.")
                        // VentanaConvocatoriaSugerida(entrenadorVM.equipo.value)

                        VentanaConvocatoriaSugerida(
                            entrenadorVM,
                            onAceptar={pantallaActiva.value = "Inicio"}
                            )

                    }
                }
            }
        }
}

@Composable
fun equipos_Entrenador(
    loginVM: LoginViewModel,
    entrenadorVM: EntrenadorViewModel

) {

    val email = loginVM.getCurrentUser()?.email
    val equipos by entrenadorVM.equipos.collectAsState()

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
//    var opciones = listOf<String>()

    LaunchedEffect(Unit) {
        entrenadorVM.equiposDisponibles(email!!)
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {
        Text(
            text = "Equipo: ",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        Box(  )
        {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { },
//                onValueChange = { selectedText = it },
                placeholder = { Text("-- Seleccione equipo --") },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .width(180.dp)
                    .clickable {
                        expanded = true
                    }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false })
            {
                equipos.forEach { equipo ->
                    DropdownMenuItem(text = { Text(text = equipo) }, onClick = {
                        expanded = false
                        selectedText = equipo
                        entrenadorVM.setEquipo(equipo)
                    })
                }
            }
        }
    }
}
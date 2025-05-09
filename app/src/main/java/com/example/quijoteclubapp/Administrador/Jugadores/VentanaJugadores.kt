package com.example.quijoteclubapp.Administrador.Jugadores

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quijoteclubapp.Administrador.CategoriaEquipos.DatosCategoriaEquipoViewModel
import com.example.quijoteclubapp.R

@Composable
fun VentanaJugadores(
    enAddJugador: () -> Unit,
    datosCategoriaEquiposVM: DatosCategoriaEquipoViewModel,
    datosJugadorVM: DatosJugadoresViewModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()) {


            Text(
                text = "Ventana Jugadores",
                color = colorResource(R.color.texto),
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.height(20.dp))
            categoria_AdminJugadores(datosCategoriaEquiposVM)
            equipos_AdminJugadores(datosCategoriaEquiposVM, datosJugadorVM)
        }
            FloatingActionButton(
                onClick = { enAddJugador() },
                containerColor = colorResource(R.color.botones),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir jugador",
                    tint = colorResource(R.color.textoBotones) // El color del icono "+"
                )
            }


    }
}

@Composable
fun categoria_AdminJugadores(datosCategoriaEquiposVM: DatosCategoriaEquipoViewModel) {

    val categorias by datosCategoriaEquiposVM.categorias.collectAsState()

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        datosCategoriaEquiposVM.categoriasDisponibles()
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {
        Text(
            text = "Categoria: ",
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
                placeholder = { Text("-- Seleccione categoría --") },
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
                categorias.forEach { categoria ->
                    DropdownMenuItem(text = { Text(text = categoria) }, onClick = {
                        expanded = false
                        selectedText = categoria
                        datosCategoriaEquiposVM.setCategoria(categoria)
                    })
                }
            }
        }

    }
}

@Composable
fun equipos_AdminJugadores(
    datosCategoriaEquiposVM: DatosCategoriaEquipoViewModel,
    datosJugadorVM: DatosJugadoresViewModel
) {

    val equipos by datosCategoriaEquiposVM.equipos.collectAsState()
    val categoriaSeleccionada by datosCategoriaEquiposVM.categoria

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
//    var opciones = listOf<String>()

    // Cada vez que cambia la categoría, carga los equipos
    LaunchedEffect(categoriaSeleccionada) {
        if (categoriaSeleccionada.isNotEmpty()) {
            datosCategoriaEquiposVM.equiposPorCategoria(categoriaSeleccionada)
        }
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
                        datosJugadorVM.setEquipo(equipo)
                    })
                }
            }
        }

    }
}

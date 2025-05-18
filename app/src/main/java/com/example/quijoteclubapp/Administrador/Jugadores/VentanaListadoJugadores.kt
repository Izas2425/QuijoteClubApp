package com.example.quijoteclubapp.Administrador.Jugadores

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quijoteclubapp.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip

@Composable
fun VentanaListadoJugadores(datosJugadorVM: DatosJugadoresViewModel,
                            enAceptar: () -> Unit) {

    val jugadores by datosJugadorVM.jugadores.collectAsState()

    Text(
        text = "Ventana  listado de jugadores",
        color = colorResource(R.color.texto),
        fontSize = 20.sp,
        modifier = Modifier.padding(10.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // 2 columnas
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(jugadores) { jugador ->
            Card(
                modifier = Modifier

                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp, start = 12.dp, end = 12.dp) // sin bottom
                        .fillMaxWidth()
                ) {
                    Row(
//                    modifier = Modifier
//                        .padding(12.dp)
//                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Columna izquierda: nombre y dorsal
                        Column(
                            modifier = Modifier.weight(1f) // ocupa todo el espacio restante
                        ) {
                            Text(
                                text = jugador.nombre,
                                color = colorResource(R.color.texto),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Dorsal: ${jugador.dorsal}",
                                color = colorResource(R.color.texto),
                                fontSize = 12.sp
                            )
                        }

                        // Columna derecha: imagen
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            AsyncImage(
                                model = jugador.fotoPerfil,
                                contentDescription = "Foto de ${jugador.nombre}",
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                    // Fila inferior: iconos de acción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
//                        .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            // Acción de actualizar
                            // datosJugadorVM.actualizarJugador(jugador)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Actualizar jugador"
                            )
                        }
                        IconButton(onClick = {
                            // Acción de eliminar
                            // datosJugadorVM.eliminarJugador(jugador)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Eliminar jugador",
//                            tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
        // Botón como último ítem scrollable
        item {
            Button(
                onClick = { enAceptar() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.botones),
                    contentColor = colorResource(R.color.textoBotones)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Volver")
            }
        }
    }
}


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
import androidx.compose.ui.draw.clip

@Composable
fun VentanaListadoJugadores(datosJugadorVM: DatosJugadoresViewModel,
                            enAceptar: () -> Unit) {

    val jugadores by datosJugadorVM.jugadores.collectAsState()
Column (modifier = Modifier.fillMaxSize()) {
    Text(
        text = "Ventana  listado de jugadores",
        color = colorResource(R.color.texto),
        fontSize = 20.sp,
        modifier = Modifier.padding(10.dp)
    )


//    LazyColumn {
//        items(jugadores) { jugador ->
//            Text("- ${jugador.nombre} ${jugador.apellidos}")
//        }
//    }

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
//                    .aspectRatio(1f), // cuadrado
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {

                Column(
//                        modifier = Modifier.weight(1f)
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center,
//                        modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            jugador.nombre,
                            color = colorResource(R.color.texto),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp)
                        )
//                        Spacer(modifier = Modifier.width(160.dp))
                        Spacer(modifier = Modifier.weight(1f)) // empuja la imagen a la derecha
                        AsyncImage(
                            model = jugador.fotoPerfil,
                            contentDescription = "Foto de ${jugador.nombre}",
                            modifier = Modifier
                                .size(60.dp)
//                                    .clip(RoundedCornerShape(50))
                        )


                    }
                    Text(
                        "Dorsal: ${jugador.dorsal}",
                        color = colorResource(R.color.texto),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

    }

//    Spacer(Modifier.height(20.dp))

    Button(
        onClick = { enAceptar() },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones),
            contentColor = colorResource(R.color.textoBotones)
        )
    )
    {
        Text("Volver")
    }
}

}
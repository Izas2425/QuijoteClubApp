package com.example.quijoteclubapp.Administrador.Jugadores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quijoteclubapp.R

@Composable
fun VentanaAltaJugador(enJugadorGuardado: () -> Unit){
    Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()) {


        Text(
            text = "Ventana alta Jugadores",
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )


        Button(onClick = { enJugadorGuardado() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.botones), // Color de fondo del botón
                contentColor = colorResource(R.color.textoBotones) // Color del texto
            )
            ) {
            Text("Guardar y volver",

                )
        }

    }
}
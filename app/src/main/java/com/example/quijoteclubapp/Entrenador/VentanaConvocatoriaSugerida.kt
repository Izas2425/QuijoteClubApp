package com.example.quijoteclubapp.Entrenador

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.quijoteclubapp.R

@OptIn(UnstableApi::class)
@Composable
fun VentanaConvocatoriaSugerida(
    entrenadorVM: EntrenadorViewModel,
    onAceptar:() ->Unit
){
    val convocatoria by entrenadorVM.convocatoriaSugerida.collectAsState()
    val partidos by entrenadorVM.partidos.collectAsState()
    val equipo by entrenadorVM.equipo

    LaunchedEffect(partidos) {
        entrenadorVM.prepararUltimosPartidosParaConvocatoria()
        entrenadorVM.sugerirConvocatoriaDesdeUltimosPartidos()
    }

    Column {
        Spacer(modifier = Modifier.height(200.dp))
        Text("   Convocatoria sugerida:")
        convocatoria.forEach {
            Spacer(modifier = Modifier.height(10.dp))
            Text("   Dorsal ${it.dorsal} - Promedio índice ACB: ${it.promedioIndice}")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {

                onAceptar()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.botones),
                contentColor = colorResource(R.color.textoBotones)
            ),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Aceptar")
        }
    }
}
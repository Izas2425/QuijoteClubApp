package com.example.quijoteclubapp.Entrenador

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi

@OptIn(UnstableApi::class)
@Composable
fun VentanaConvocatoriaSugerida(entrenadorVM: EntrenadorViewModel){
    val convocatoria by entrenadorVM.convocatoriaSugerida.collectAsState()
    val partidos by entrenadorVM.partidos.collectAsState()
    val equipo by entrenadorVM.equipo

    LaunchedEffect(partidos) {
        entrenadorVM.prepararUltimosPartidosParaConvocatoria()
        entrenadorVM.sugerirConvocatoriaDesdeUltimosPartidos()
    }

    Column {
        Spacer(modifier = Modifier.height(200.dp))
        Text("Convocatoria sugerida:")
        convocatoria.forEach {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Dorsal ${it.dorsal} - Promedio índice: ${it.promedioIndice}")
        }
    }
}
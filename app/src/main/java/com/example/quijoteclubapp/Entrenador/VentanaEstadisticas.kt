package com.example.quijoteclubapp.Entrenador

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.quijoteclubapp.Modelos.PuntosJugador
import com.example.quijoteclubapp.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun VentanaEstadisticas(
    equipo: String,
    entrenadorVM: EntrenadorViewModel,
    onAceptar:() ->Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp)
            .fillMaxWidth()) {

        Spacer(modifier = Modifier.height(
            100.dp))
        Text(
            text = "Ventana estadísticas",
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )


        Spacer(modifier = Modifier.height(10.dp))

        partidosPorEquipo(equipo, entrenadorVM)


        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = {
                entrenadorVM.limpiarPartidoSeleccionado()
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

@Composable
fun partidosPorEquipo(equipo:String, entrenadorVM: EntrenadorViewModel){

    val partidos by entrenadorVM.partidos.collectAsState()
    val partido by entrenadorVM.partidoSeleccionado.collectAsState()
    val fechas by entrenadorVM.fechaPartidos.collectAsState()

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

//    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        entrenadorVM.partidosDelEquipo(equipo)
    }



    Column(
        modifier = Modifier
            .padding(6.dp)
//            .verticalScroll(scrollState)
    ) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {
        Text(
            text = "Partido: ",
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
                placeholder = { Text("-- Seleccione partido --") },
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
                fechas.forEach { fecha ->
                    DropdownMenuItem(text = { Text(text = fecha) }, onClick = {
                        expanded = false
                        selectedText = fecha
                        entrenadorVM.setFechaSeleccionada(fecha)
                        entrenadorVM.partidoPorFecha(fecha, equipo)
                    })
                }
            }
        }


    }
        partido?.let { partidoSeleccionado ->
            val convocados = partidoSeleccionado.convocados

            // Estadísticas calculadas
            val totalPuntos = convocados.sumOf { it.puntos }
            val puntosRival = partido!!.puntosEquipoContrario
            val diferencia = totalPuntos - puntosRival
            val asistencias = convocados.sumOf { it.asistencias }
            val robos = convocados.sumOf { it.robos }
            val tapones = convocados.sumOf { it.tapones }
            val perdidas = convocados.sumOf { it.perdidas }
            val faltas = convocados.sumOf { it.faltasCometidas }
            val rebotes = convocados.sumOf { it.rebotes }

            // Tiros fallados disponibles
            val tirosLibresFallados = convocados.sumOf { it.tirosLibresFallados }
            val tirosDeCampoFallados = convocados.sumOf { it.tirosDeCampoFallados }

            Column(modifier = Modifier.padding(16.dp)) {
                Text("📊 Estadísticas del Partido", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text("📅 Fecha: ${partidoSeleccionado.fecha}")
                Text("🆚 Rival: ${partidoSeleccionado.equipoContrario}")
                Text("✅ Puntos equipo: $totalPuntos")
                Text("❌ Puntos rival: $puntosRival")
                Text("➖ Diferencia: $diferencia")

                Spacer(modifier = Modifier.height(12.dp))

                Text("📋 Detalle del equipo:")
                Text("• Asistencias: $asistencias")
                Text("• Rebotes: $rebotes")
                Text("• Robos: $robos")
                Text("• Tapones: $tapones")
                Text("• Pérdidas de balón: $perdidas")
                Text("• Faltas cometidas (FP): $faltas")

                Spacer(modifier = Modifier.height(12.dp))

                Text("🎯 Tiros fallados:")
                Text("• Tiros libres fallados: $tirosLibresFallados")
                Text("• Tiros de campo fallados: $tirosDeCampoFallados")

                Spacer(modifier = Modifier.height(16.dp))

//                // ✅ NUEVO: calcular mapa y mostrar gráfico
//                val puntosPorJugador: Map<String, Int> =
//                    convocados.associate { it.dorsal to it.puntos }
//                GraficaPuntosPorJugador(puntosPorJugador)

                val indicesACB = entrenadorVM.calcularIndiceACBPorJugador(convocados)
                GraficaIndicesACBPorJugador(indicesACB)
            }
        }
    }
}

@Composable
fun GraficaPuntosPorJugador(puntosPorJugador: Map<String, Int>) {
    val entries = puntosPorJugador.entries.mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.value.toFloat())
    }

    val barDataSet = BarDataSet(entries, "Puntos por jugador").apply {
        color = ColorTemplate.MATERIAL_COLORS[0]
        valueTextSize = 12f
    }

    val barData = BarData(barDataSet)

    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                legend.isEnabled = true
                xAxis.valueFormatter = IndexAxisValueFormatter(puntosPorJugador.keys.toList())
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                axisRight.isEnabled = false
                animateY(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    )
}

@Composable
fun GraficaIndicesACBPorJugador(indicesACB: Map<String, Int>) {
    val entries = indicesACB.entries.mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.value.toFloat())
    }

    val context = LocalContext.current
    val colorInt = ContextCompat.getColor(context, R.color.botones)

    val barDataSet = BarDataSet(entries, "Índice ACB por jugador").apply {
        color = colorInt
//        color = ColorTemplate.MATERIAL_COLORS[0]
        valueTextSize = 12f
    }

    val barData = BarData(barDataSet)

    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                legend.isEnabled = true
                xAxis.valueFormatter = IndexAxisValueFormatter(indicesACB.keys.toList())
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                axisRight.isEnabled = false
                animateY(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    )
}


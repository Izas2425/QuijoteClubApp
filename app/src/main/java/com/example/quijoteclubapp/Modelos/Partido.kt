package com.example.quijoteclubapp.Modelos

data class Partido(
    val id: String = "",
    val fecha: String = "",
    val longitud: Double? = 0.0,
    val latitud: Double? = 0.0,
    val convocados: List<String> = emptyList(), // ids jugadores
    val hora: String = "",
    val autobus: Boolean = false,
    val horaSalida: String = "",
    val horaLlegada: String = "",
    val fotos: List<String> = emptyList(),
    val puntosEquipoLocal: Int = 0,
    val puntosEquipoVisitante: Int = 0
)

package com.example.quijoteclubapp.Modelos

data class Partido(
    val id: String = "",
    val fecha: String = "",
    val longitud: Double? = 0.0,
    val latitud: Double? = 0.0,
    val convocados: List<PuntosJugador> = emptyList(), //
    val hora: String = "",
    val autobus: Boolean = false,
    val horaSalida: String = "",
    val horaLlegada: String = "",
    val fotos: List<String> = emptyList(),
    val puntosEquipoClub: Int = 0,
    val puntosEquipoContrario: Int = 0,
    val categoria: String ="",
    val equipoClub: String ="",
    val equipoContrario: String=""
)

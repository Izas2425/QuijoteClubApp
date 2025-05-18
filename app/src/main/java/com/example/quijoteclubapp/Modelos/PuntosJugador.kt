package com.example.quijoteclubapp.Modelos

data class PuntosJugador(
    val dorsal: String="",
    val puntos: Int = 0,
    val rebotes: Int = 0,
    val asistencias: Int = 0,
    val robos: Int = 0,
    val tapones: Int = 0,
    val tirosLibresFallados: Int = 0,
    val tirosDeCampoFallados: Int = 0,
    val perdidas: Int = 0,
    val taponesRecibidos: Int = 0,
    val faltasRecibidas: Int = 0,
    val faltasCometidas: Int = 0
)

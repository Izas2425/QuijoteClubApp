package com.example.quijoteclubapp.Modelos

data class Jugador(
    val id: String = "",
    val numRegistro: Int = 0,
    val nombre: String = "",
    val apellidos: String = "",
    val dni: String = "",
    val fechaNacimiento: String = "",
    val categoria: String = "",
    val email: String = "",
    val movilJugador: String = "",
    val movilPadreMadre: String = "",
    val fotoPerfil: String = "",
    val compite: Boolean = false,
    val dorsal: Int = 0,
    val equipo: String =""
)

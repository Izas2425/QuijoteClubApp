package com.example.quijoteclubapp.Modelos

data class Equipo(
    val id: String = "",
    val nombre: String = "",
    val jugadores: List<String> = emptyList(), // lista de IDs de jugadores
    val entrenador: String = "", // idEntrenador
    val fotos: List<String> = emptyList(),
    val categoria: String =""
)

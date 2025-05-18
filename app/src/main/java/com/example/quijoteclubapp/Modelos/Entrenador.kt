package com.example.quijoteclubapp.Modelos

data class Entrenador(
    val id: String = "",
    val email: String="",
    val nombre: String = "",
    val apellidos: String = "",
    val equipos: List<String> = emptyList()
)

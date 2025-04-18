package com.example.quijoteclubapp.Modelos

data class Aficionado(
    val idAficionado: String?= null,
    val email: String = "",
    val castegoriasFavoritas: List<String> = emptyList()
)

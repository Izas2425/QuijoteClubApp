package com.example.quijoteclubapp.Modelos

data class PadreMadre(
    val idPadreMadre : String?= null,
    val email: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val movil: String = "",
    val numRegistroHijo:  List<String> = emptyList()
)

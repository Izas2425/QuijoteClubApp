package com.example.quijoteclubapp.Modelos

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Eventos(
    var id: String? = null,
    val descripcion: String = "",
    val longitud: Double? = 0.0,
    val latitud: Double? = 0.0,
    val fecha: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val hora: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
    val fotos: List<String> = emptyList()
)

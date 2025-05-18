package com.example.quijoteclubapp.Entrenador

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quijoteclubapp.Modelos.Entrenador
import com.example.quijoteclubapp.Modelos.Partido
import com.example.quijoteclubapp.Modelos.PuntosJugador
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EntrenadorViewModel: ViewModel() {
    // Para la base de datos que contendrá  a los entrenadores
    val db = Firebase.firestore

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _equipos = MutableStateFlow<List<String>>(emptyList())
    val equipos: StateFlow<List<String>> = _equipos

    private val _equipo = mutableStateOf("")
    val equipo: State<String> get() = _equipo

    private val _partidos = MutableStateFlow<List<Partido>>(emptyList())
    val partidos: StateFlow<List<Partido>> = _partidos

    private val _fechaPartidos = MutableStateFlow<List<String>>(emptyList())
    val fechaPartidos: StateFlow<List<String>> = _fechaPartidos

    private val _fechaSeleccionada = mutableStateOf("")
    val fechaSeleccionada: State<String> get() = _fechaSeleccionada

    private val _partidoSeleccionado = MutableStateFlow<Partido?>(null)
    val partidoSeleccionado: StateFlow<Partido?> = _partidoSeleccionado

    private val  _convocados = MutableStateFlow<PuntosJugador?>(null)
    val convocados :StateFlow<PuntosJugador?> = _convocados



    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    fun setEmail(nuevoEmail: String){
        if (nuevoEmail.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _email.value= nuevoEmail
            _Error.value = null
        }
    }

    fun setEquipo(nuevoEquipo: String){
        if (nuevoEquipo.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _equipo.value= nuevoEquipo
            _Error.value = null
        }
    }

    fun setFechaSeleccionada(nuevoFecha: String){
        if (nuevoFecha.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _fechaSeleccionada.value= nuevoFecha
            _Error.value = null
        }
    }

    fun equiposDisponibles(email: String){
        db.collection("entrenadores")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { resultado ->
                val entrenadorDoc = resultado.documents.firstOrNull()
                if (entrenadorDoc != null) {
                    val entrenador = entrenadorDoc.toObject(Entrenador::class.java)
                    val nombresEquipos = entrenador?.equipos ?: emptyList()
                    _equipos.value = nombresEquipos
                } else {
                    _Error.value = "No se encontró el entrenador con ese email."
                }
            }
            .addOnFailureListener { e ->
                _Error.value = "Error al buscar entrenador: ${e.message}"
            }
    }

    fun partidosDelEquipo(equipo:String){
        db.collection("partidos")
            .whereEqualTo("equipoClub", equipo)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.mapNotNull { it.toObject(Partido::class.java) }
                _partidos.value = lista

                // se guarda las fechas para mostrarlas en el desplegable
                val fechas = lista.map { it.fecha }
                _fechaPartidos.value = fechas
            }
            .addOnFailureListener { e ->
                _Error.value = "Error al cargar los partidos: ${e.message}"
            }
    }

    fun partidoPorFecha(fecha: String, equipo: String) {
        db.collection("partidos")
            .whereEqualTo("equipoClub", equipo)
            .whereEqualTo("fecha", fecha)
            .get()
            .addOnSuccessListener { result ->
                val partido = result.mapNotNull { it.toObject(Partido::class.java) }.firstOrNull()
                _partidoSeleccionado.value = partido


            }
            .addOnFailureListener { e ->
                _Error.value = "Error al cargar el partido: ${e.message}"
            }
    }

}
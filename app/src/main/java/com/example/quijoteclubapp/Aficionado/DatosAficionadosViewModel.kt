package com.example.quijoteclubapp.Aficionado

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.quijoteclubapp.Colecciones
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DatosAficionadosViewModel : ViewModel(){

    // Para la base de datos que contendrá  a los aficionados
    val db = Firebase.firestore

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _categoriasFavoritas = mutableStateListOf<String>()
    val amigcategoriasFavoritasos: SnapshotStateList<String> get() = _categoriasFavoritas



}
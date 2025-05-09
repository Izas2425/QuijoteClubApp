package com.example.quijoteclubapp.Administrador.CategoriaEquipos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DatosCategoriaEquipoViewModel: ViewModel() {

    // Para la base de datos que contendrá  a los categoriasEquipos
    val db = Firebase.firestore

    private val _categorias = MutableStateFlow<List<String>>(emptyList())
    val categorias: StateFlow<List<String>> = _categorias

    private val _equipos = MutableStateFlow<List<String>>(emptyList())
    val equipos: StateFlow<List<String>> = _equipos

    private val _categoria = mutableStateOf("")
    val categoria: State<String> get() = _categoria

    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    fun setCategoria(nuevaCategoria: String){
        if (nuevaCategoria.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _categoria.value= nuevaCategoria
            _Error.value = null
        }
    }

    fun equiposPorCategoria(categoriaNombre: String) {
        db.collection("categoriasEquipos")
            .whereEqualTo("nombre", categoriaNombre)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documento = querySnapshot.documents[0]
                    val equipos = documento.get("equipos") as? List<String> ?: emptyList()
                    _equipos.value = equipos
                    _categoria.value = categoriaNombre
                } else {
                    _equipos.value = emptyList()
                    _Error.value = "No se encontró la categoría $categoriaNombre"
                }
            }
            .addOnFailureListener { e ->
                _Error.value = "Error al cargar equipos: ${e.message}"
            }
    }

    fun categoriasDisponibles() {
        db.collection("categoriasEquipos")
            .get()
            .addOnSuccessListener { result ->
                val nombresCategorias = result.documents.mapNotNull { doc ->
                    doc.getString("nombre")
                }
                _categorias.value = nombresCategorias
            }
            .addOnFailureListener { e ->
                _Error.value = "Error al cargar categorías: ${e.message}"
            }
    }

}
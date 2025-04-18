package com.example.quijoteclubapp.AdminSettings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AdminSettingsViewModel:  ViewModel() {
    private val db = Firebase.firestore

    private val _adminPin = mutableStateOf("")
    val adminPin:  State<String> get() = _adminPin

    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    fun cargarPinAdmin() {
        db.collection("adminSettings")
            .document("adminPin")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val pin = document.getString("pin")
                    if (pin != null) {
                        _adminPin.value = pin
                    } else {
                        _Error.value = "El campo 'pin' está vacío en Firestore"
                    }
                } else {
                    _Error.value = "No se encontró el PIN de admin"
                }
            }
            .addOnFailureListener { exception ->
                _Error.value = "Error al obtener el PIN: ${exception.message}"
            }
    }

    fun verificarPin(entradaUsuario: String): Boolean {
        return entradaUsuario == _adminPin.value
    }

}
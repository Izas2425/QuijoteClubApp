package com.example.quijoteclubapp.PadreMadre

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quijoteclubapp.Colecciones
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PadresMadresViewModel: ViewModel() {

    // Para la base de datos que contendrá  a los padres/madres
    val db = Firebase.firestore

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _nombre = mutableStateOf("")
    val nombre: State<String> get() = _nombre

    private val _apellidos = mutableStateOf("")
    val apellidos: State<String> get() = _apellidos

    private val _movil = mutableStateOf("")
    val movil: State<String> get() = _movil

    private val _numRegistroHijos = mutableStateListOf<String>()
    val numRegistroHijos: SnapshotStateList<String> get() = _numRegistroHijos

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

    fun setNombre(nuevoNombre: String){
        if (nuevoNombre.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _nombre.value= nuevoNombre
            _Error.value = null
        }
    }

    fun setApellidos(nuevoApellidos: String){
        if (nuevoApellidos.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _apellidos.value= nuevoApellidos
            _Error.value = null
        }
    }

    fun setMovil(nuevoMovil: String){
        if (nuevoMovil.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _movil.value= nuevoMovil
            _Error.value = null
        }
    }

    // función que se usa para el primer hij@
    fun setNumRegistroHijos(nuevoRegistro: String) {
        if (nuevoRegistro.isEmpty()) {
            _Error.value = "El número de registro no puede estar vacío"
        } else {
            _numRegistroHijos.clear()
            _numRegistroHijos.add(nuevoRegistro)
            _Error.value = null
        }
    }

    // función que se usa cuando tenga más de un hij@ en el club
    fun addNumRegistroHijo(nuevoRegistro: String) {
        if (nuevoRegistro.isEmpty()) {
            _Error.value = "El número de registro no puede estar vacío"
        } else {
            _numRegistroHijos.add(nuevoRegistro)
            _Error.value = null
        }
    }

    // Se guarda el padre/madre en la bd y se limpian los datos del VM
    fun addPadreMadre(emailLogeado:String){
        setEmail(emailLogeado)
        if (email.value.isEmpty() || nombre.value.isEmpty() || apellidos.value.isEmpty() || movil.value.isEmpty()) {
            _Error.value = "Todos los campos son obligatorios"
            return
        }

        if (numRegistroHijos.isEmpty()) {
            _Error.value = "Debe haber al menos un número de registro"
            return
        }

        val datos = hashMapOf(
            "email" to email.value,
            "nombre" to nombre.value,
            "apellidos" to apellidos.value,
            "movil" to movil.value,
            "numRegistroHijos" to numRegistroHijos.toList()
        )

        db.collection(Colecciones.PadresMadres)
            .document(email.value)  // email como ID único
            .set(datos)
            .addOnSuccessListener {
                limpiarDatos()
                _Error.value = null
            }
            .addOnFailureListener { e ->
                _Error.value = "Error al guardar en la base de datos: ${e.message}"
            }


    }

    fun limpiarDatos(){
        _email.value = ""
        _nombre.value = ""
        _apellidos.value = ""
        _movil.value = ""
        _numRegistroHijos.clear()
        _Error.value = null
    }


}
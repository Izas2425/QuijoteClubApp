package com.example.quijoteclubapp.DatosUsuario

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quijoteclubapp.Colecciones
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class DatosUsuarioViewModel: ViewModel (){

    // Para la base de datos que contendrá  a los usuarios
    val db = Firebase.firestore

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _role = mutableStateOf("")
    val role: State<String> get() = _role

    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    fun setRole(nuevoRole: String){
        if (nuevoRole.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _role.value= nuevoRole
            _Error.value = null
        }
    }

    fun setEmail(nuevoEmail: String){
        if (nuevoEmail.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _email.value= nuevoEmail
            _Error.value = null
        }
    }

    // Se actualiza el role del usuario
    fun actualizarUsuario(emailLogeado:String){
        Log.e("Izaskun"," Estoy en el actualizar role de usuario.El email logeado es: ${emailLogeado}" )

        val usuariosRef = db.collection(Colecciones.Usuarios)

        usuariosRef.document(emailLogeado)
            .set(mapOf("role" to role.value), SetOptions.merge())
            .addOnSuccessListener {
                Log.e("Izaskun", "Perfil actualizado con éxito")
               _role.value = ""

            }
            .addOnFailureListener { e ->
                Log.e("Izaskun", "Error al actualizar el perfil: $e")
            }
    }


}
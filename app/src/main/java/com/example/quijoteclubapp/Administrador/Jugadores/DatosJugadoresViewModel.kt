package com.example.quijoteclubapp.Administrador.Jugadores

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.quijoteclubapp.Colecciones
import com.example.quijoteclubapp.Modelos.Jugador
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

class DatosJugadoresViewModel : ViewModel(){

    // Para la base de datos que contendrá  a los jugadores
    val db = Firebase.firestore

    private val _nombre = mutableStateOf("")
    val nombre: State<String> get() = _nombre

    private val _apellidos = mutableStateOf("")
    val apellidos: State<String> get() = _apellidos

    private val _dni = mutableStateOf("")
    val dni: State<String> get() = _dni

    private val _categoria = mutableStateOf("")
    val categoria: State<String> get() = _categoria

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _movilJugador = mutableStateOf("")
    val movilJugador: State<String> get() = _movilJugador

    private val _movilPadreMadre = mutableStateOf("")
    val movilPadreMadre: State<String> get() = _movilPadreMadre

    private val _numRegistro = mutableStateOf(0)
    val numRegistro: State<Int> get() = _numRegistro

    private val _fotoPerfil = mutableStateOf("")
    val fotoPerfil:State<String> get() = _fotoPerfil

    // fechaNacimiento: Solo la fecha sin la parte de la hora
    private val _fechaNacimiento = MutableStateFlow(LocalDateTime.now().toLocalDate())
    val fechaNacimiento: StateFlow<LocalDate> get() = _fechaNacimiento

    private val _compite = mutableStateOf(false)
    val compite:State<Boolean> get() = _compite

    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    // para las fotos
    private val _urlPfp = MutableLiveData<String>()
    val urlPfp: LiveData<String> = _urlPfp

    private val _imageUri = MutableLiveData<Uri>(Uri.EMPTY)
    val imageUri: LiveData<Uri> get() = _imageUri

    private val _imageFile = MutableLiveData<File?>()
    val imageFile: LiveData<File?> get() = _imageFile

    val jugadorAdded = MutableStateFlow(false)


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

    fun setNumRegistro(nuevoNumRegistro: Int){

            _numRegistro.value= nuevoNumRegistro
            _Error.value = null
    }

    fun setDni(nuevoDni: String){
        if (nuevoDni.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _dni.value= nuevoDni
            _Error.value = null
        }
    }

    fun setCategoria(nuevoCategoria: String){
        if (nuevoCategoria.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _categoria.value= nuevoCategoria
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

    fun setFechaNacimiento(nuevaFechaNacimiento: LocalDate) {
        _fechaNacimiento.value = nuevaFechaNacimiento
    }

    fun setMovilPadreMadre(nuevoMovilPadreMadre: String){
        if (nuevoMovilPadreMadre.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _movilPadreMadre.value= nuevoMovilPadreMadre
            _Error.value = null
        }
    }

    fun setMovilJugador(nuevoMovilJugador: String){
        if (nuevoMovilJugador.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            _movilJugador.value= nuevoMovilJugador
            _Error.value = null
        }
    }

    fun setCompite(nuevoCompite: Boolean){
            _compite.value= nuevoCompite
            _Error.value = null
    }

    fun setFotoPerfil(nuevaFotoPerfil: String){
        Log.e("Izaskun","Estoy en setFotoPerfil ${nuevaFotoPerfil}")
        if (nuevaFotoPerfil.isEmpty()){
            _Error.value= "El campo no puede estar vacio"
        }else{
            Log.e("Izaskun","Estoy ${nuevaFotoPerfil}")
            _fotoPerfil.value= nuevaFotoPerfil
            _Error.value = null
        }
    }
    fun getEmail():String{
        return email.value
    }

    // Para las fotos
    fun updateImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun setImageFile(file: File) {
        _imageFile.value = file
    }

    fun setUrlPfp(url: String) {
        this._urlPfp.value = url
    }

    fun iniciarJugadorAdded(){
        jugadorAdded.value = false
    }

    fun actualizarFotoPerfil(numRegistro:String){
        val jugadoresRef = db.collection(Colecciones.Jugadores)
        val perfilActualizado = mapOf(
            "fotoPerfil" to fotoPerfil.value
        )
        jugadoresRef.document(numRegistro)
            .set(mapOf("perfil" to perfilActualizado), SetOptions.merge())
    }

    fun cargarNumRegistro(){
        db.collection(Colecciones.Jugadores)
            .orderBy("numRegistro", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documentos ->
                val maxNum = documentos.firstOrNull()?.getLong("numRegistro")?.toInt() ?: 0
                _numRegistro.value = maxNum + 1
            }
            .addOnFailureListener {
                _numRegistro.value = 1
                Log.e("CargarNumRegistro", "Error al obtener el número de registro")
            }
    }

    fun limpiarValores(){
        _dni.value=""
        _email.value=""
        _movilJugador.value = ""
        _movilPadreMadre.value = ""
        _fechaNacimiento.value = null
        _nombre.value=""
        _apellidos.value=""
        _categoria.value=""
        _fotoPerfil.value=""
        _numRegistro.value= 0

    }

    fun addJugador(){
        Log.e("Izaskun"," Estoy en addJugador.El num registro es: ${numRegistro.value}" )

        val jugadoresRef = db.collection(Colecciones.Jugadores)

        val jugador = Jugador(
            numRegistro = numRegistro.value,
            nombre = nombre.value,
            apellidos = apellidos.value,
            dni = dni.value,
            fechaNacimiento = fechaNacimiento.value.toString(),
            categoria = categoria.value,
            email = email.value,
            fotoPerfil = fotoPerfil.value,
            movilJugador = movilJugador.value,
            movilPadreMadre = movilPadreMadre.value,
            compite = compite.value
        )
        // Usa el numRegistro como el ID del documento
        jugadoresRef.document(numRegistro.value.toString()) // Aquí le das el ID al documento
            .set(jugador) // Usa set para guardar el jugador
            .addOnSuccessListener {
                Log.d("Izaskun", "Jugador añadido con ID: ${numRegistro.value}")
                jugadorAdded.value = true
                limpiarValores() // Limpiar los valores después de añadir
            }
            .addOnFailureListener { e ->
                Log.e("Izaskun", "Error al añadir jugador", e)
            }
    }

    // esta función añade un usuario a la bd si no existe ya
    fun addJugadorToUsuarios (email: String){
        val usuariosRef = db.collection(Colecciones.Usuarios)

        // se comprueba si existe el jugador en la bd
        // si ya existe no se añade
        usuariosRef
            .whereEqualTo("email", email)
            // .whereEqualTo("role", usuario.role)
            .get()
            .addOnSuccessListener { consulta ->
                if(!consulta.isEmpty){
                    Log.e("Izaskun", "El usuario ya existe")
                }else{

                    val nuevoUsuario = hashMapOf(
                        "email" to email,
                        "role" to "Jugador"
                    )
                    usuariosRef.document(email)
                        .set(nuevoUsuario)
                        .addOnSuccessListener { Log.e("Izaskun", "Usuario añadido con exito") }
                        .addOnFailureListener { Log.e("Izaskun", "Error al añadir el usuario") }
                }
            }
            .addOnFailureListener {  Log.e("Izaskun", "Error al consultar al usuario") }
    }

    fun comprobarNumRegistroExiste(numRegistro: Int, onResultado: (Boolean) -> Unit) {
        db.collection(Colecciones.Jugadores)
            .whereEqualTo("numRegistro", numRegistro)
            .limit(1)
            .get()
            .addOnSuccessListener { documentos ->
                val existe = !documentos.isEmpty
                onResultado(existe)
            }
            .addOnFailureListener { e ->
                Log.e("ComprobarNumRegistro", "Error al comprobar el número de registro", e)
                onResultado(false) // o podrías usar null si prefieres manejar errores aparte
            }
    }



}
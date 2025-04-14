package com.example.quijoteclubapp.Login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quijoteclubapp.Colecciones
import com.example.quijoteclubapp.Modelos.Usuario
import com.example.quijoteclubapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel(){
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val TAG = "Izaskun"

    // Para la base de datos que contendrá  a los usuarios
    val db = Firebase.firestore

    private val _usuarios = mutableStateListOf<Usuario>()
    val usuarios: List<Usuario> get() = _usuarios

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _role = mutableStateOf("")
    val role: State<String> get() = _role

    private val _Error = MutableLiveData<String?>()
    val Error : LiveData<String?> = _Error

    //Variables para los estados...
    val isLoading = MutableStateFlow(false)
    val loginSuccess = MutableStateFlow(false)
    val loginGoogleSuccess = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null


    fun loginWithEmail(email: String, password: String) {
        isLoading.value = true
        errorMessage.value = null
        loginSuccess.value = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    loginSuccess.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Error desconocido"
                }
            }
    }

    private fun updateUserStatus(email: String, isOnline: Boolean) {
        val usuariosRef = db.collection(Colecciones.Usuarios)

        // Actualiza el campo 'enLinea' del usuario
        usuariosRef.document(email)
            .update("enLinea", isOnline)
            .addOnSuccessListener {
                Log.d(TAG, "Estado en línea actualizado a $isOnline para $email")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error al actualizar el estado en línea para $email")
            }
    }


    fun registerWithEmail(email: String, password: String) {
        isLoading.value = true
        errorMessage.value = null
        loginSuccess.value = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    loginSuccess.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Error desconocido"
                }
            }
    }

    // Permite realizar el inicio de sesión de un usuario en Firebase utilizando Google Sing-In
    // idToken es el token de identificación proporcionado por Google despues de que el usuario
    // selecciona una cuenta de Google para la autenticación
    fun loginWithGoogle(idToken: String) {
        isLoading.value = true
        errorMessage.value = null
        loginSuccess.value = false

        // se utiliza el token proporcionado por Google para generar un objeto AuthCredential de Firebase
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // Se llama al método singInWithCredential de Firebase Authentication para autenticar al usuario
        // con las credenciales obtenidas
        auth.signInWithCredential(credential)
            // Se verifican los resultados de la operación
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    loginSuccess.value = true
                    loginGoogleSuccess.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Error desconocido"
                }
            }
    }

    fun signOut(context: Context) {
        Log.d(TAG, "signOut() llamado ${loginGoogleSuccess.value}")
        if (loginGoogleSuccess.value) {
            //El usuario inició sesión con Google
            val googleSignInClient = GoogleSignIn.getClient(
                context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            )

            googleSignInClient.revokeAccess().addOnCompleteListener { revokeTask ->
                if (revokeTask.isSuccessful) {
                    Log.d(TAG, "Acceso revocado correctamente")
                    auth.signOut()
                    Log.d(TAG, "Sesión cerrada correctamente")
                } else {
                    Log.e(TAG, "Error al revocar el acceso")
                }
            }
        } else {
            //El usuario no inició sesión con Google (email/contraseña u otro proveedor)
            auth.signOut()
            Log.d(TAG, "Sesión cerrada para usuario no Google")
        }

        //Actualizar el estado de las variables de UI
        loginGoogleSuccess.value = false
        loginSuccess.value = false
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // esta función comprueba si existe algún usuario en la colección Usuarios con ese email
    suspend fun existeUsuario():Boolean{
        val currentUser = FirebaseAuth.getInstance().currentUser
        val email = currentUser?.email
        return try{
            val querySnapshot = db.collection(Colecciones.Usuarios)
                .whereEqualTo("email", email)
                .get()
                .await()
            !querySnapshot.isEmpty
        }catch (e: Exception){
            false
        }
    }

    suspend fun getRolePorEmail(email: String):String?{
        val usuariosRef = db.collection(Colecciones.Usuarios)
        val querySnapshot = usuariosRef
            .whereEqualTo("email", email)
            .get()
            .await()
        return if (querySnapshot.isEmpty){
            null
        }else{
            val usuario = querySnapshot.documents[0]
            usuario.get("role") as? String
        }
    }

    // esta función añade un usuario a la bd si no existe ya
    fun addUsuario (email: String){
        val usuariosRef = db.collection(Colecciones.Usuarios)

        // se comprueba si existe el usuario en la bd
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
                        "role" to ""
                    )
                    usuariosRef.document(email)
                        .set(nuevoUsuario)
                        .addOnSuccessListener { Log.e("Izaskun", "Usuario añadido con exito") }
                        .addOnFailureListener { Log.e("Izaskun", "Error al añadir el usuario") }
                }
            }
            .addOnFailureListener {  Log.e("Izaskun", "Error al consultar al usuario") }
    }

}
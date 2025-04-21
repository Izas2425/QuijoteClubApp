package com.example.quijoteclubapp.Login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.quijoteclubapp.AdminSettings.AdminSettingsViewModel
import com.example.quijoteclubapp.DatosUsuario.DatosUsuarioViewModel
import com.example.quijoteclubapp.R
import com.example.quijoteclubapp.Rutas
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginVM: LoginViewModel,
    datosUsuarioVM: DatosUsuarioViewModel,
    adminSettingsVM: AdminSettingsViewModel
){
    val context = LocalContext.current

    val isLoading by loginVM.isLoading.collectAsState()
    val loginSuccess by loginVM.loginSuccess.collectAsState()
    val errorMessage by loginVM.errorMessage.collectAsState()

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isRegistering by remember { mutableStateOf(false) } // Para alternar entre Login y Registro

    var emailUsuario by remember { mutableStateOf("") }

    // lanza la pantalla con las cuentas de google para elegir
    // Una vez seleccionada la cuenta de google, obtiene el idToken para pasarlo a loginWithGoogle
    // que lo envia a firebase para autenticarse

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result
            val idToken = account?.idToken
            if (idToken != null) {
                loginVM.loginWithGoogle(idToken)
            } else {
                Toast.makeText(context, "Error obteniendo token de Google", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun launchGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.client_id)) //Agrega tu client_id de Firebase, lo encontrarás en google-services.json, oauth-client/clinet_id
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInLauncher.launch(googleSignInClient.signInIntent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a QuijoteClub" ,
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logoquijotclubapp),
            contentDescription = "Imagen decorativa",
            modifier = Modifier
                .height(80.dp) // Puedes ajustar el tamaño a tu gusto
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = if (isRegistering) "Registrar Cuenta" else "Iniciar Sesión",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isRegistering) {
                    loginVM.registerWithEmail(email.text, password.text)
                } else {
                    loginVM.loginWithEmail(email.text, password.text)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.botones), // Color de fondo del botón
                contentColor = colorResource(R.color.textoBotones) // Color del texto
            )
        ) {
            Text(if (isRegistering) "Registrar" else "Iniciar Sesión",
                color = colorResource(R.color.texto),
                fontSize = 15.sp,)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { launchGoogleSignIn() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.botones), // Color de fondo del botón
                contentColor = colorResource(R.color.textoBotones) // Color del texto
            )
        ) {
            Text("Iniciar Sesión con Google")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { isRegistering = !isRegistering }) {
            Text(
                text = if (isRegistering) "¿Ya tienes cuenta? Inicia Sesión" else "¿No tienes cuenta? Regístrate",
                color = colorResource(R.color.texto),
                fontSize = 15.sp,
            )
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        var mostrarDialogoRole by remember { mutableStateOf(false) }
        var mostrarDialogoPin by remember { mutableStateOf(false) }


        LaunchedEffect(loginSuccess) { //

            if (loginSuccess)  {
                Toast.makeText(context, "Login correcto", Toast.LENGTH_SHORT).show()
                // se guarda el email del usuario que se ha identificado en emailUsuario
                emailUsuario = loginVM.getCurrentUser()?.email!!
                //datosUsuarioVM.setEmail(emailUsuario!!)
                // si el usuario no está en la bd, lo añade
                // y como no existe en la bd, es su primera vez por lo que rellena el perfil
                if (!loginVM.existeUsuario()){
                    loginVM.addUsuario(emailUsuario!!)
                    // la primera vez le solicita el role: aficionado o padre/madre
                    mostrarDialogoRole = true

                // No es la primera vez que entra
                }else {
                    val role = loginVM.getRolePorEmail(emailUsuario!!)
                    when (role){
                        "Aficionado" -> navController.navigate(Rutas.aficionado)
                        "PadreMadre" -> navController.navigate(Rutas.padreMadre)
                        "Admin" ->{
                            adminSettingsVM.cargarPinAdmin()
                            mostrarDialogoPin = true
                            //navController.navigate(Rutas.admin)
                        }

                    }
                }
            }
        }
        if (mostrarDialogoRole){
            DialogoSeleccionarRole(navController, datosUsuarioVM,emailUsuario){
                mostrarDialogoRole = false
            }
        }

        if (mostrarDialogoPin){
            DialogoPinAdmin(adminSettingsVM, navController){
                mostrarDialogoPin = false
            }
        }


        /*
        LaunchedEffect evita múltiples ejecuciones

        LaunchedEffect está diseñado para manejar este tipo de situaciones porque:

            Se ejecuta una sola vez por cada cambio del valor clave (en este caso, loginSuccess).
            Una vez que el efecto se ejecuta, no se vuelve a ejecutar hasta que loginSuccess cambie nuevamente.

        Esto garantiza que las acciones, como mostrar un Toast o navegar, ocurran solo cuando realmente cambien las condiciones, evitando comportamientos inesperados debido a las recomposiciones.
         */
    }

}

// Esta funcion muestra un dialogo con dos botones para que elija su role
// Entre los dos que puede ser un usuario que se da de alta: aficionado o padre/madre
@Composable
fun  DialogoSeleccionarRole(
    navController: NavHostController,
    datosUsuarioVM: DatosUsuarioViewModel,
    emailUsuario: String,
    onDismiss: () -> Unit
){
    var mostrar by remember { mutableStateOf(true)  }
    if (mostrar) {
        Dialog(onDismissRequest = { mostrar=false },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true)
        ){

            Column(modifier = Modifier
                .width(350.dp)
                .padding(20.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Eres: ",
                    color = colorResource(R.color.texto),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        mostrar = false
                        datosUsuarioVM.setRole("Aficionado")
                        datosUsuarioVM.actualizarUsuario(emailUsuario)
                        navController.navigate(Rutas.aficionado) // Navega a la pantalla de usuario estándar
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.botones), // Color de fondo del botón
                        contentColor = colorResource(R.color.textoBotones) // Color del texto
                    )
                ) {
                    Text("Aficionado")
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        mostrar = false
                        datosUsuarioVM.setRole("PadreMadre")
                        datosUsuarioVM.actualizarUsuario(emailUsuario)
                        navController.navigate(Rutas.registrarse) // Navega a la pantalla de administrador
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.botones), // Color de fondo del botón
                        contentColor = colorResource(R.color.textoBotones) // Color del texto
                    )
                ) {
                    Text("Padre/Madre")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        mostrar = false
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.botones), // Color de fondo del botón
                        contentColor = colorResource(R.color.textoBotones) // Color del texto
                    )
                ) {
                    Text("Cancelar")
                }
            }
//            }
        }
    }

}

@Composable
fun DialogoPinAdmin(
    adminVM: AdminSettingsViewModel,
    navController: NavHostController,
    onDismiss: () -> Unit
) {
    var pinIngresado by remember { mutableStateOf("") }
    var errorPin by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {

            Text(
                text = "Introduce el PIN de Admin: ",
                color = colorResource(R.color.texto),
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = pinIngresado,
                onValueChange = { pinIngresado = it },
                label = {
                    Text(text = "PIN: ",
                        color = colorResource(R.color.texto),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp))
                        },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(8.dp))

            errorPin?.let {
                Text(it, color = Color.Red)
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Cancelar ",
                        color = colorResource(R.color.texto),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp))
                }

                TextButton(onClick = {
                    if (adminVM.verificarPin(pinIngresado)) {
                        navController.navigate(Rutas.admin)
                        onDismiss()
                    } else {
                        errorPin = "PIN incorrecto"
                    }
                }) {
                    Text(text = "Aceptar ",
                        color = colorResource(R.color.texto),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp))
                }
            }
        }
    }
}

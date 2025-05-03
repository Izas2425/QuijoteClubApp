package com.example.quijoteclubapp.Administrador.Jugadores

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.quijoteclubapp.R
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.quijoteclubapp.BuildConfig
import com.example.quijoteclubapp.Login.LoginViewModel


@Composable
fun VentanaAltaJugador(
    datosJugadorVM: DatosJugadoresViewModel,
    loginVM: LoginViewModel,
    enJugadorGuardado: () -> Unit
)
    {
        val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Habilita el scroll
//            .padding(8.dp)
    ) {
        Text(
            text = "Alta Jugadores",
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        numRegistro(datosJugadorVM)
        dni(datosJugadorVM)
        nombre(datosJugadorVM)
        apellidos(datosJugadorVM)
        movilJugador(datosJugadorVM)
        movilPadreMadre(datosJugadorVM)
        fechaNacimiento(datosJugadorVM)
        categoria(datosJugadorVM)
        email(datosJugadorVM)
        compite(datosJugadorVM)
        tomarFoto(datosJugadorVM, context)
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            botonAceptarAltaJugador(datosJugadorVM, loginVM) { enJugadorGuardado() }
            botonCancelarAltaJugador(datosJugadorVM){enJugadorGuardado()}
        }
    }
}

@Composable
fun numRegistro(datosJugadorVM: DatosJugadoresViewModel){

    val numRegistro by remember{ datosJugadorVM.numRegistro}

    // Solo se llama una vez al iniciar
    LaunchedEffect(Unit) {
        datosJugadorVM.cargarNumRegistro()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(6.dp)
    ) {

        Text(
            text = "Nº de registro:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        OutlinedTextField(
            value = numRegistro.toString(),
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .width(100.dp)

        )
    }
}

@Composable
fun nombre(datosJugadorVM: DatosJugadoresViewModel ){
    val nombre by remember {datosJugadorVM.nombre}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = "Nombre:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = nombre,
            onValueChange = { nuevoNombre ->
                datosJugadorVM.setNombre(nuevoNombre)
            },
            modifier = Modifier.width(180.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}

@Composable
fun apellidos(datosJugadorVM: DatosJugadoresViewModel ){
    val apellidos by remember {datosJugadorVM.apellidos}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = "Apellidos:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = apellidos,
            onValueChange = { nuevoApellidos ->
                datosJugadorVM.setApellidos(nuevoApellidos)
            },
            modifier = Modifier.width(180.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun fechaNacimiento(datosJugadorVM: DatosJugadoresViewModel){

    var fechaNacimiento by remember { mutableStateOf<LocalDate?>(null) }

    val fechaActual = LocalDate.now()

    val contexto = LocalContext.current

    val ventanaFecha = android.app.DatePickerDialog(
        contexto,
        { _, año, mes, dia ->
            val fechaSeleccionada = LocalDate.of(año, mes + 1, dia)
            fechaNacimiento = fechaSeleccionada
            datosJugadorVM.setFechaNacimiento(fechaNacimiento!!)
        },
        fechaActual.year,
        fechaActual.monthValue - 1,
        fechaActual.dayOfMonth
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(6.dp)
    )
    {
        Text(
            text = "F. Nacimiento:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        OutlinedTextField(
            value = fechaNacimiento?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.width(150.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = colorResource(R.color.texto),
                disabledBorderColor = Color.Transparent, // quita borde
                containerColor = Color.Transparent, // fondo transparente
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
        IconButton(onClick = {ventanaFecha.show()})
        {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Fecha de nacimiento",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun categoria(datosJugadorVM: DatosJugadoresViewModel){

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var opciones = listOf<String>("Prebenjamin", "Benjamin", "Alevín masculino",
        "Infantil masculino", "Cadete masculino", "Junior masculino", "Sub-22 masculino", "Senior masculino",
       "Alevín femenino", "Infantil femenino",
        "Cadete femenino", "Junior femenino", "Sub-22 femenino", "Senior femenino")

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {
        Text(
            text = "Categoria: ",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        Box(  )
        {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .width(180.dp)
                    .clickable {
                        expanded = true
                    }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false })
            {
                opciones.forEach { opcion ->
                    DropdownMenuItem(text = { Text(text = opcion) }, onClick = {
                        expanded = false
                        selectedText = opcion
                        datosJugadorVM.setCategoria(opcion)
                    })
                }
            }
        }

    }
}

@Composable
fun email(datosJugadorVM: DatosJugadoresViewModel ){
    val email by remember {datosJugadorVM.email}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = "Email:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = email,
            onValueChange = { nuevoEmail ->
                datosJugadorVM.setEmail(nuevoEmail)

            },
            modifier = Modifier.width(180.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}

@Composable
fun dni(datosJugadorVM: DatosJugadoresViewModel ){
    val dni by remember {datosJugadorVM.dni}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(
            text = "DNI:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = dni,
            onValueChange = { nuevoDni ->
                datosJugadorVM.setDni(nuevoDni)
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}

@Composable
fun compite(datosJugadorVM: DatosJugadoresViewModel){

    var estadoCompite by remember { mutableStateOf(false) }
    var estadoSEnable by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text("Compite",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )

        Checkbox(
            checked = estadoCompite,
            enabled = estadoSEnable,
            onCheckedChange = { estadoCompite = !estadoCompite }
        )


    }
    datosJugadorVM.setCompite(estadoCompite)
}

@Composable
fun tomarFoto(datosJugadorVM: DatosJugadoresViewModel, contexto: Context)
{
    val imageUri by datosJugadorVM.imageUri.observeAsState(Uri.EMPTY)
    val imageFile by datosJugadorVM.imageFile.observeAsState(null)

    //Lanza permisos y cámara.
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageFile?.let { file ->
                datosJugadorVM.updateImageUri(Uri.fromFile(file))

                val imagePath = imageUri.toString()
                datosJugadorVM.setFotoPerfil(imagePath)
            }
        } else {
            datosJugadorVM.updateImageUri(Uri.EMPTY)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            val file = File.createTempFile("pfp", ".jpg", contexto.cacheDir)
            datosJugadorVM.setImageFile(file)
            cameraLauncher.launch(FileProvider.getUriForFile(contexto, BuildConfig.APPLICATION_ID + ".provider", file))
        }
    }
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            //Muestra la imagen estándar si no se ha seleccionado nada.
            if (imageUri != Uri.EMPTY) {
                //Esta función es de COIL.
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                        .clickable {
                            // Al hacer clic sobre la imagen, lanza la cámara
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.pfp),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxWidth()
                        .   size(80.dp)
                        .clickable {
                            // Al hacer clic sobre la imagen, lanza la cámara
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        },
                    alignment = Alignment.Center
                )
            }
        }
    }
}

@Composable
fun botonAceptarAltaJugador(
    datosJugadorVM: DatosJugadoresViewModel,
    loginVM: LoginViewModel,
    onClick: () -> Unit
){
    val email = datosJugadorVM.getEmail()
    val jugadorAdded by datosJugadorVM.jugadorAdded.collectAsState()
    val bandera = remember { mutableStateOf(false) }


    val nombre = datosJugadorVM.nombre.value
    val apellidos = datosJugadorVM.apellidos.value
    val emailFormulario = datosJugadorVM.email.value
    val categoria = datosJugadorVM.categoria.value
    val fechaNacimiento = datosJugadorVM.fechaNacimiento.collectAsState().value

    val formularioValido = nombre.isNotBlank()
            && apellidos.isNotBlank()
            && emailFormulario.isNotBlank()
            && categoria.isNotBlank()
            && fechaNacimiento != null

    LaunchedEffect(jugadorAdded) {
        if (jugadorAdded && !bandera.value) {
            bandera.value = true
//            datosJugadorVM.addJugadorToUsuarios(email)
//            datosJugadorVM.addJugador()
            onClick()
        }
    }


    Button(
        onClick = {
            // se vuelve a poner a falso
            datosJugadorVM.iniciarJugadorAdded()

            loginVM.registerWithEmail(email, "Jugador")
            datosJugadorVM.addJugadorToUsuarios(email)
            datosJugadorVM.addJugador()
//            onClick()
                  },
        enabled = formularioValido,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones),
            contentColor = colorResource(R.color.textoBotones)
        )
    ) {
        Text("Guardar y volver")
    }

}

@Composable
fun movilJugador(datosJugadorVM: DatosJugadoresViewModel ){
    val movilJugador by remember {datosJugadorVM.movilJugador}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(
            text = "Móvil jugador:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = movilJugador,
            onValueChange = { nuevoMovilJugador ->
                datosJugadorVM.setMovilJugador(nuevoMovilJugador)
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}

@Composable
fun movilPadreMadre(datosJugadorVM: DatosJugadoresViewModel ){
    val movilPadreMadre by remember {datosJugadorVM.movilPadreMadre}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(
            text = "Móvil padre/madre:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = movilPadreMadre,
            onValueChange = { nuevoMovilPadreMadre ->
                datosJugadorVM.setMovilPadreMadre(nuevoMovilPadreMadre)
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.texto),
                unfocusedTextColor = colorResource(R.color.texto),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.texto), // borde activo
                unfocusedIndicatorColor = colorResource(R.color.texto).copy(alpha = 0.5f), // borde apagado
                cursorColor = colorResource(R.color.texto)
            )
        )
    }
}

@Composable
fun botonCancelarAltaJugador(datosJugadorVM: DatosJugadoresViewModel, onClick: () -> Unit){
    Button(
        onClick = {
            datosJugadorVM.limpiarValores()
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones),
            contentColor = colorResource(R.color.textoBotones)
        )
    ) {
        Text("Cancelar")
    }

}
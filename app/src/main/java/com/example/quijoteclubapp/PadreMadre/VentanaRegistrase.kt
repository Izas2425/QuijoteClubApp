package com.example.quijoteclubapp.PadreMadre

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import androidx.navigation.NavHostController
import com.example.quijoteclubapp.Administrador.Jugadores.DatosJugadoresViewModel
import com.example.quijoteclubapp.Login.LoginViewModel
import com.example.quijoteclubapp.R
import com.example.quijoteclubapp.Rutas

@Composable
fun VentanaRegistrarse(
    navController: NavHostController,
    loginVM: LoginViewModel,
    padresMadresVM: PadresMadresViewModel,
    contexto: Context,
    datosJugadorVM: DatosJugadoresViewModel
){
    val emailLogeado = loginVM.getCurrentUser()?.email

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.logoquijotclubapp), // reemplaza con tu imagen
            contentDescription = null,
            contentScale = ContentScale.Crop, // Rellena toda la pantalla
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f)
        )


        Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()) {

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Registro para Padres/Madres",
                color = colorResource(R.color.texto),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))
            nombre(padresMadresVM)
            apellidos(padresMadresVM)
            movil(padresMadresVM)
            numRegistroHijo(padresMadresVM)
            Spacer(modifier = Modifier.height(10.dp))
            Row( modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically){
                if (emailLogeado != null) {
                    botonAceptarRegistro(navController,padresMadresVM, emailLogeado, datosJugadorVM)
                }
                Spacer(modifier = Modifier.width(8.dp))
                botonCancelarRegistro(navController)
            }


        }
    }
}

@Composable
fun nombre(padresMadresVM: PadresMadresViewModel ){
    val nombre by remember {padresMadresVM.nombre}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Nombre:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = nombre,
            onValueChange = { nuevoNombre ->
                padresMadresVM.setNombre(nuevoNombre)
            },
            modifier = Modifier.width(240.dp),
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
fun apellidos(padresMadresVM: PadresMadresViewModel){
    val apellidos by remember {padresMadresVM.apellidos}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Apellidos:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = apellidos,
            onValueChange = { nuevoApellidos ->
                padresMadresVM.setApellidos(nuevoApellidos)
            },
            modifier = Modifier.width(230.dp),
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
fun movil(padresMadresVM: PadresMadresViewModel){
    val movil by remember {padresMadresVM.movil}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Móvil:",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = movil,
            onValueChange = { nuevoMovil ->
                padresMadresVM.setMovil(nuevoMovil)
            },
            modifier = Modifier.width(250.dp),
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
fun numRegistroHijo(padresMadresVM: PadresMadresViewModel){
    val numRegistro = if (padresMadresVM.numRegistroHijos.isNotEmpty()) {
        padresMadresVM.numRegistroHijos[0]
    } else {
        ""
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Datos de tu hijo/a",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Núm. de registro:",
                color = colorResource(R.color.texto),
                fontSize = 15.sp,
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = numRegistro,
                onValueChange = { nuevoNumRegistro ->
                    padresMadresVM.setNumRegistroHijos(nuevoNumRegistro)
                },
                modifier = Modifier.width(150.dp),
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
}

@Composable
fun botonAceptarRegistro(
    navController: NavHostController,
    padresMadresVM: PadresMadresViewModel,
    emailLogeado: String,
    datosJugadorVM: DatosJugadoresViewModel
)
{
    val habilitarBoton = remember { mutableStateOf(false) }

    val numRegistro: Int? = padresMadresVM.numRegistroHijos.firstOrNull()?.toIntOrNull()

    LaunchedEffect(numRegistro) {
        if (numRegistro != null) {
            if (numRegistro > 0) {
                datosJugadorVM.comprobarNumRegistroExiste(numRegistro!!) { existe ->
                    habilitarBoton.value = existe
                    if (!existe){
                        Log.e("Izaskun", "No existe el número de registro")
                    }
                }
            } else {
                habilitarBoton.value = false
            }
        }
    }

    Button(
        onClick = {
            padresMadresVM.addPadreMadre(emailLogeado)
            navController.navigate(Rutas.padreMadre)
        },
        enabled = habilitarBoton.value,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones),
            contentColor = colorResource(R.color.textoBotones)
        )
    ) {
        Text(text = "Aceptar")
    }

}

@Composable
fun botonCancelarRegistro(navController: NavHostController) {
    Button(onClick = {navController.navigate(Rutas.login)},
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.botones), // Color de fondo del botón
            contentColor = colorResource(R.color.textoBotones) // Color del texto
        )
    )
    {
        Text(text = "Cancelar")
    }
}



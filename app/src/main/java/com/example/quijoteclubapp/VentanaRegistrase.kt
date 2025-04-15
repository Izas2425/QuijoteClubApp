package com.example.quijoteclubapp

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quijoteclubapp.DatosUsuario.DatosUsuarioViewModel
import com.example.quijoteclubapp.Login.LoginViewModel

@Composable
fun VentanaRegistrarse(
    navController: NavHostController,
    loginVM: LoginViewModel,
    datosUsuarioVM: DatosUsuarioViewModel,
    contexto: Context
){
    val emailLogeado = loginVM.getCurrentUser()?.email

    Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()) {

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Registro",
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        eligirRole(datosUsuarioVM)

    }
}

@Composable
fun eligirRole(datosUsuarioVM: DatosUsuarioViewModel){
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var opciones = listOf<String>("Aficionado", "Padre/Madre")

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Soy : ",
            color = colorResource(R.color.texto),
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )

        Box(modifier = Modifier.width(140.dp)) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable {
                        expanded = true
                    }
                    .wrapContentWidth()
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false })
            {
                opciones.forEach { opcion ->
                    DropdownMenuItem(text = { Text(text = opcion) }, onClick = {
                        expanded = false
                        selectedText = opcion
                       datosUsuarioVM.setRole(opcion)

                    })
                }
            }
        }
    }
}
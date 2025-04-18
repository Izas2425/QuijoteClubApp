package com.example.quijoteclubapp.Administrador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quijoteclubapp.R

@Composable
fun VentanaAdministrador(){
    Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()) {

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Ventana Administrador",
            color = colorResource(R.color.texto),
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))


    }
}
package com.example.hangmanapp

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource


@Composable
fun ResultScreen(navController: NavController, victoria: Boolean, intentos: Int, difficult: String) {

    val rojoCarmesi = colorResource(id = R.color.rojo_carmesi)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        if (victoria) {
            Text(
                text = "Lo has conseguido después de $intentos fallos!!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(50.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.victoria),
                contentDescription = "victoria",
                modifier = Modifier
                    .size(200.dp)
            )
        } else {
            Text(
                text = "Buena suerte la próxima vez!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(50.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.derrota),
                contentDescription = "derrota",
                modifier = Modifier
                    .size(200.dp)
            )
        }
        Button(
            onClick = { navController.navigate(Routes.Pantalla3.crearRuta(difficult)) },
            colors = ButtonDefaults.buttonColors(
                containerColor = rojoCarmesi
            ),
        ) {
            Text(
                text = "PLAY AGAIN")
        }
        Button(
            onClick = { navController.navigate(Routes.Pantalla2.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            ),
        ) {
            Text(
                text = "MENU")
        }
    }
}
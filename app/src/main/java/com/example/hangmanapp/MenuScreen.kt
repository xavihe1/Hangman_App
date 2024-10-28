package com.example.hangmanapp

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    var selectedText by remember { mutableStateOf("Easy") } // Cambiado para establecer "Easy" como valor por defecto
    var expanded by remember { mutableStateOf(false) }
    val difficulty = listOf("Easy", "Hard")
    val rojoCarmesi = colorResource(id = R.color.rojo_carmesi)
    val grisClaro = colorResource(id = R.color.gris_claro)
    val verdeHierba = colorResource(id = R.color.verde_hierba)

    Column(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxWidth(),
        horizontalAlignment  = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.hangman_image),
            contentDescription = null,
            modifier = Modifier
                .padding(70.dp)
                .size(270.dp)
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
        ){
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = {
                    Text(text = "Dificultat", color = Color.Gray) // Cambiamos el color del texto del label
                },
                textStyle = TextStyle(color = Color.Black), // Cambiamos el color del texto
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                enabled = false,
                readOnly = true,
                shape = RoundedCornerShape(8.dp), // Agregamos bordes redondeados
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black, // Cambiamos el color del texto
                    cursorColor = Color.Black, // Cambiamos el color del cursor
                    focusedBorderColor = rojoCarmesi, // Cambiamos el color del borde cuando está enfocado
                    unfocusedBorderColor = Color.Gray // Cambiamos el color del borde cuando no está enfocado
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop Down Arrow",
                        tint = Color.Gray // Cambiamos el color del icono
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                difficulty.forEach { dificultat ->
                    DropdownMenuItem(
                        text = { Text(text = dificultat) },
                        onClick = {
                            expanded = false
                            selectedText = dificultat
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Agrega espacio entre la imagen y los botones
        Button(
            onClick = {
                // Aquí se utiliza el valor seleccionado, que ya tendrá "Easy" como valor predeterminado
                navController.navigate(Routes.Pantalla3.crearRuta(selectedText))
            },
            modifier = Modifier
                .height(56.dp) // Reducimos la altura del botón "PLAY"
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = rojoCarmesi,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "PLAY",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold), // Reducimos el tamaño del texto
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    modifier = Modifier.size(32.dp) // Reducimos el tamaño del icono
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Agrega espacio entre los botones
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            var mostraDialog by remember { mutableStateOf(false) }

            Button(
                onClick = { mostraDialog = true },
                modifier = Modifier
                    .height(54.dp) // Aumentamos la altura del botón "HELP"
                    .padding(horizontal = 40.dp), // Ajustamos el espacio horizontal del botón "HELP"
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verdeHierba,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "HELP",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold), // Aumentamos el tamaño del texto
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Help",
                        modifier = Modifier.size(28.dp) // Reducimos ligeramente el tamaño del icono
                    )
                }
            }
            HelpDialog(mostraDialog, { mostraDialog = false })
        }
    }
}


@Composable
fun HelpDialog(mostraDialog: Boolean, onDismiss: () -> Unit) {
    if (mostraDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Column(
                Modifier
                    .background(Color.LightGray)
                    .padding(24.dp)
                    .fillMaxWidth()) {
                Text(
                    text = "Les regles del joc són molt sencilles, consta de un joc d'un únic jugador." +
                            " El jugador abans d' entrar a la partida haurà de sel·lecionar una dificultat (fàcil o difícil)." +
                            " Al començar la partida, amb les lletres del abecedari que apareixen a la pantalla, el jugador haurà d' endevinar la paraula oculta." +
                            " Constarà d' uns intents limitats en els que, per cada error del jugador, s' anirà dibuixant una persona penjada." +
                            " Un cop el jugador hagi fallat tant com per haver completat el dibuix de la persona penjada, s' acabarà el joc.")
            }
        }
    }
}
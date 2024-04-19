package com.example.hangmanapp

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val difficulty = listOf("Easy", "Hard")
    Column(
        modifier = Modifier,
        horizontalAlignment  = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.hangman_image),
            contentDescription = null,
            modifier = Modifier
                .padding(70.dp)
                .size(250.dp)
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
        ){
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { expanded = true }
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
                        })
                }
            }
        }
        Button(
            onClick = { navController.navigate(Routes.Pantalla3.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            ),
        ) {
            Text(
                text = "PLAY")
        }
        Box(
            modifier = Modifier
                .padding(20.dp),
            contentAlignment = Alignment.Center) {

            var mostraDialog by remember { mutableStateOf(false) }

            Button(
                onClick = { mostraDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(
                    text = "HELP")
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
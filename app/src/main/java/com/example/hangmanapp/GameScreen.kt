package com.example.hangmanapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource


@Composable
fun GameScreen(navController: NavController, difficult: String) {
    val botones = ('A'..'Z').toList()
    var intentos by remember { mutableIntStateOf(0) }
    var numImagen by remember { mutableIntStateOf(0) }
    val palabraEscogida = remember { mutableStateOf(palabras(difficult)) }
    var palabraEscondida by remember { mutableStateOf("_".repeat(palabraEscogida.value.length)) }

    val imagenHangman = when (numImagen) {
        0 -> R.drawable.fallo0
        1 -> R.drawable.fallo1
        2 -> R.drawable.fallo2
        3 -> R.drawable.fallo3
        4 -> R.drawable.fallo4
        5 -> R.drawable.fallo5
        else -> R.drawable.fallo6
    }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imagenHangman),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        Box {
            Text(
                text = palabraEscondida,
                fontSize = 35.sp,
                letterSpacing = 5.sp,
                color = Color.Black
            )
        }

        // Mostrar las teclas en una cuadrícula de filas y columnas
        val filas = 5
        val columnas = 6

        for (fila in 0 until filas) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (columna in 0 until columnas) {
                    val i = fila * columnas + columna
                    if (i < botones.size) {
                        val letra = botones[i]
                        var colorDeLasTeclas by remember { mutableStateOf(Color(0xFF1E88E5)) } // Azul oscuro
                        var letraUsada by remember { mutableStateOf(false) } // Estado para verificar si la letra fue usada

                        Box(
                            modifier = Modifier
                                .padding(4.dp) // Reduce el padding
                                .background(colorDeLasTeclas, shape = RoundedCornerShape(10.dp))
                                .size(52.dp) // Tamaño de las teclas un poco más pequeño
                                .border(2.dp, Color.Black, RoundedCornerShape(10.dp)) // Borde negro
                                .clickable {
                                    if (!letraUsada) { // Comprobar si la letra no fue usada antes
                                        letraUsada = true // Marcar letra como usada

                                        // Comprueba si la letra está en la palabra
                                        if (letra in palabraEscogida.value) {
                                            colorDeLasTeclas = Color.Green // Cambia a verde si está en la palabra
                                            palabraEscondida = palabraEscogida.value.indices.joinToString("") {
                                                if (palabraEscogida.value[it] == letra) letra.toString() else palabraEscondida[it].toString()
                                            }
                                        } else {
                                            colorDeLasTeclas = Color.Red // Cambia a rojo si no está
                                            intentos++
                                            numImagen++
                                        }
                                    }
                                }
                        ) {
                            Text(
                                text = letra.toString(),
                                color = Color.White, // Color del texto
                                fontSize = 18.sp, // Cambiar tamaño de texto para que se ajuste
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }

    // Navegación en caso de victoria o derrota
    if (palabraEscondida == palabraEscogida.value) {
        navController.navigate(Routes.Pantalla4.crearRuta(true, intentos, difficult))
    } else if (numImagen >= 6) { // Verifica si se llegó a 6 intentos
        navController.navigate(Routes.Pantalla4.crearRuta(false, intentos, difficult))
    }
}



@Composable
fun Game(navController: NavController, difficult: String) {
    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameScreen(navController = navController, difficult = difficult)
    }
}

fun palabras(difficult: String): String {
    val palabrasEasy = listOf("PEZ", "PERRO", "ELEFANTE", "LIBRO", "RIO", "MANDARINA", "RESFRIADO", "PELOTA", "JUGUETE")
    val palabrasHard = listOf("METACRILATO", "ACETONA", "VENTRILOQUIA", "NITROGLICERINA", "RESTRICCION", "ESPARADRAPO", "AMBIDIESTRO", "QUIROMANCIA", "FILANTROPIA", "HIDROFOBIA", "MISTICISMO", "JINETE", "COEXISTIR", "OXIGENO", "INEXORABLE", "AMBIGUEDAD", "XILOFONO", "ENIGMA", "JACTANCIA", "COAGULO", "QUIMERA", "XENOFOBIA", "EXEQUIAS", "CONVEXO", "GIROSCOPIO", "QUILATE", "JEROGLIFICO", "GUIRNALDA", "YUNQUE", "YUGULAR", "CRATER", "ZAFIRO", "ZAMBULLIDA", "YUXTAPUESTO", "UNIFORME", "QUEBRADIZO", "MULTINACIONAL", "CANGREJO", "LONGANIZA", "TRIVIAL", "ALMOHADILLA", "CRISTALINO", "TELEVISOR", "DIAGNOSTICO", "HEMIPLEJIA", "PROCEDIMIENTO", "DIAMANTE", "CHAQUETA", "HORIZONTE", "PINZAS", "ALMACENAR", "PLAN")

    return when (difficult.lowercase()) {
        "easy" -> palabrasEasy.random()
        else -> palabrasHard.random()
    }
}

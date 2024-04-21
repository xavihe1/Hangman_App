package com.example.hangmanapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource


@Composable
fun GameScreen(navController: NavController, difficult: String) {

    val botones by remember { mutableStateOf(arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'))}
    var intentos by remember { mutableIntStateOf(0) }
    var numImagen by remember { mutableIntStateOf(0) }
    val dificultad by remember{ mutableStateOf( palabras(difficult)) }
    var palabraEscondida by remember { mutableStateOf("_".repeat(dificultad.length)) }
    var azulCielo = colorResource(id = R.color.azul_cielo)

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
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = (imagenHangman)),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
        )
        Box(
            modifier = Modifier
        ){
            Text(
                text = palabraEscondida,
                fontSize = 35.sp,
                letterSpacing = 5.sp
            )
        }
        val palabraNuevaEscondida = palabraEscondida.toCharArray()
        val palabraEscogidaNueva = dificultad.uppercase().toCharArray()
        val filas = 6
        val columnas = 6

        for (fila in 0 until filas) {
            Row {
                for (columna in 0 until columnas) {
                    val i = fila * columnas + columna
                    if (i <= botones.lastIndex) {
                        var correcto = false
                        val lletra = botones[i]
                        var colorDeLasTeclas by remember { mutableStateOf(azulCielo) }
                        Box(
                            modifier = Modifier
                                .padding(3.dp)
                                .background(colorDeLasTeclas)
                                .width(50.dp)
                                .height(50.dp)
                                .border(
                                    width = 2.dp,
                                    color = azulCielo,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    for (letra in dificultad.indices) {
                                        if (lletra == dificultad[letra]) {
                                            correcto = true
                                            palabraEscogidaNueva[letra] = lletra
                                        }
                                    }
                                    palabraEscondida = String(palabraNuevaEscondida)
                                    if (!correcto) {
                                        colorDeLasTeclas = Color.Red
                                        intentos++
                                        numImagen++
                                    } else {
                                        colorDeLasTeclas = Color.Green
                                        for (letra in dificultad.indices) {
                                            if (lletra == dificultad[letra]) {
                                                palabraNuevaEscondida[letra] = lletra
                                            }
                                        }
                                        palabraEscondida = String(palabraNuevaEscondida)
                                    }
                                }
                        ) {
                            Text(text = "$lletra", modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
    if (palabraEscondida == dificultad) {
        navController.navigate(Routes.Pantalla4.crearRuta(true, intentos, difficult))
    } else if (imagenHangman == R.drawable.fallo6) {
        navController.navigate(Routes.Pantalla4.crearRuta(false, intentos, difficult))
    }
}


@Composable
fun Game(navControler: NavController, difficult: String) {
    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameScreen(navController = navControler, difficult)
    }
}
fun palabras(difficult: String): String {
    val palabrasEasy = listOf("PEZ", "PERRO", "ELEFANTE", "LIBRO", "RIO", "MANDARINA", "RESFRIADO", "PELOTA", "JUGUETE")
    val palabrasHard = listOf("METACRILATO", "ACETONA", "VENTRILOQUIA", "NITROGLICERINA", "RESTRICCION", "ESPARADRAPO", "AMBIDIESTRO", "QUIROMANCIA", "FILANTROPIA", "HIDROFOBIA", "MISTICISMO", "JINETE", "COEXISTIR", "OXIGENO", "INEXORABLE", "AMBIGUEDAD", "XILOFONO", "ENIGMA", "JACTANCIA", "COAGULO", "QUIMERA", "XENOFOBIA", "EXEQUIAS", "CONVEXO", "GIROSCOPIO", "QUILATE", "JEROGLIFICO", "GUIRNALDA", "YUNQUE", "YUGULAR", "CRATER", "ZAFIRO", "ZAMBULLIDA", "YUXTAPUESTO", "UNIFORME", "QUEBRADIZO", "MULTINACIONAL", "CANGREJO", "LONGANIZA", "TRIVIAL", "ALMOHADILLA", "CRISTALINO", "TELEVISOR", "DIAGNOSTICO", "HEMIPLEJIA", "PROCEDIMIENTO", "DIAMANTE", "CHAQUETA", "HORIZONTE", "PINZAS", "ALMACENAR", "PLAN")
    val palabrasJuego = when (difficult) {
        "Easy"-> palabrasEasy
        else -> palabrasHard
    }
    return palabrasJuego[(palabrasJuego.indices).random()]
}
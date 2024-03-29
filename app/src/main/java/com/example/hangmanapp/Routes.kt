package com.example.hangmanapp

sealed class Routes(val route: String) {
    object Pantalla1: Routes("LaunchScreen")
    object Pantalla2: Routes("MenuScreen")
    object Pantalla3: Routes("GameScreen/{difficult}") {
        fun crearRuta(difficult: String) = "GameScreen/$difficult"
    }
    object Pantalla4: Routes("ResultScreen/{victoria}/{intentos}/{difficult}") {
        fun crearRuta(victoria: Boolean, intentos: Int, difficult: String) = "ResultScreen/$victoria/$intentos/$difficult"
    }
}

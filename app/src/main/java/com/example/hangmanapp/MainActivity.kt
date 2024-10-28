package com.example.hangmanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hangmanapp.ui.theme.HangmanAppTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HangmanAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.Pantalla1.route
                    ) {
                        composable(Routes.Pantalla1.route) { LaunchScreen(navigationController) }
                        composable(Routes.Pantalla2.route) { MenuScreen(navigationController) }

                        composable(Routes.Pantalla3.route,
                            arguments = listOf(navArgument("difficult") {type = NavType.StringType})
                        ) { backStackEntry ->
                            val difficult = backStackEntry.arguments?.getString("difficult") ?: "Easy"
                            GameScreen(navController = navigationController, difficult = difficult)
                        }

                        composable(Routes.Pantalla4.route,
                            arguments = listOf(navArgument("victoria") {type = NavType.BoolType},
                                navArgument("intentos") {type = NavType.IntType},
                                navArgument("difficult") {type = NavType.StringType})
                        ) { backStackEntry ->
                            ResultScreen(
                                navigationController,
                                backStackEntry.arguments?.getBoolean("victoria") ?: false,
                                backStackEntry.arguments?.getInt("intentos") ?: 0,
                                backStackEntry.arguments?.getString("difficult") ?: "Easy"
                            ) }
                    }
                }
            }
        }
    }
}


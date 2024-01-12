package com.example.hangmanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                            Game(
                                navigationController,
                                backStackEntry.arguments?.getString("difficult") ?: "Easy"
                            ) }

                        composable(Routes.Pantalla4.route,
                            arguments = listOf(navArgument("victoria") {type = NavType.BoolType},
                                navArgument("count") {type = NavType.IntType},
                                navArgument("difficult") {type = NavType.StringType})
                        ) { backStackEntry ->
                            ResultScreen(
                                navigationController,
                                backStackEntry.arguments?.getBoolean("victoria") ?: false,
                                backStackEntry.arguments?.getInt("count") ?: 0,
                                backStackEntry.arguments?.getString("difficult") ?: "Easy"
                            ) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanAppTheme {
        Greeting("Android")
    }
}
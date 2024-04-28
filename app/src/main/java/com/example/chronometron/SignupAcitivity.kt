package com.example.chronometron

// Android and Jetpack components
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chronometron.ui.screens.SignupScreen
import com.example.chronometron.ui.theme.ChronoMetronTheme


/**
 * SignupActivity is responsible for setting up the user interface for the signup flow.
 * It now uses a simple local management of credentials.
 */
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChronoMetronTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "signupScreen") {
                    composable("signupScreen") {
                        SignupScreen(navController)
                    }
                    // Define other destinations...
                }
            }
        }
    }
}

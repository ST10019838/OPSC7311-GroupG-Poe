package com.example.chronometron

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.example.chronometron.ui.screens.LoginScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chronometron.ui.screens.SignupScreen




// LoginActivity manages the user sign-in process using both Google sign-in and email/password authentication.
class LoginActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    // Activity result launcher to handle Google Sign-In result
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("LoginActivity", "Google sign-in result code: ${result.resultCode}, Data: ${result.data}")
        if (result.resultCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            Log.e("LoginActivity", "Sign-in failed with result code: ${result.resultCode}, Intent data: ${result.data}")
            Toast.makeText(this, "Sign-In cancelled or failed. Result Code: ${result.resultCode}, Data: ${result.data}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set content from Compose
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen") {
                composable("loginScreen") {
                    LoginScreen(
                        onLoginClick = { email, password -> signInWithEmail(email, password) },
                        onGoogleSignInClick = { signInWithGoogle() },
                        onGithubSignInClick = { /* Implement GitHub login if necessary */ },
                        onSignUpClick = { navController.navigate("signupScreen") }
                    )
                }
                composable("signupScreen") {
                    SignupScreen(navController)  // Ensure this function takes only NavController as parameter
                }
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Toast.makeText(this, "Sign In successful!", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()
        } catch (e: ApiException) {
            Log.e("LoginActivity", "Sign In failed with error code: ${e.statusCode} - ${e.message}")
            navigateToMainActivity()  // For debugging, remove or handle properly in production
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        if (CredentialsManager.validateCredential(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()
        } else {
            Toast.makeText(this, "Login failed: Invalid credentials.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSignup() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}

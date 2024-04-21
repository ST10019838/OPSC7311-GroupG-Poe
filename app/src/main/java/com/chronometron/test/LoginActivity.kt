package com.chronometron.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

// LoginActivity manages the user sign-in process using both Google sign-in and email/password authentication.
class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    // Activity result launcher to handle Google Sign-In result
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("LoginActivity", "Google sign-in result code: ${result.resultCode}, Data: ${result.data}")
        if (result.resultCode == 0) {
            // If resultCode is OK, proceed to check the data and handle the sign-in result
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            // Log and show a toast with the result code and data when the sign-in is not successful
            Log.e("LoginActivity", "Sign-in failed with result code: ${result.resultCode}, Intent data: ${result.data}")
            Toast.makeText(this, "Sign-In cancelled or failed. Result Code: ${result.resultCode}, Data: ${result.data}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In with the options specifying email request
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up listeners for UI elements
        findViewById<Button>(R.id.googleLoginButton).setOnClickListener {
            signInWithGoogle()
        }

        findViewById<TextView>(R.id.signUpText).setOnClickListener {
            navigateToSignup()
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val email = findViewById<EditText>(R.id.usernameEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()
            signInWithEmail(email, password)
        }
    }

    // Initiates the Google sign-in process
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // Handles the result from the Google Sign-In flow
    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)  // Attempt to complete the sign-in
            Toast.makeText(this, "Sign In successful!", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()
        } catch (e: ApiException) {
            // Log and show error if the sign-in fails
            Log.e("LoginActivity", "Sign In failed with error code: ${e.statusCode} - ${e.message}")
            val errorMessage = when (e.statusCode) {
                GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Sign-in cancelled"
                GoogleSignInStatusCodes.SIGN_IN_FAILED -> "Sign-in failed"
                GoogleSignInStatusCodes.INVALID_ACCOUNT -> "Invalid account"
                GoogleSignInStatusCodes.DEVELOPER_ERROR -> "Developer error - check configurations"
                else -> "Unknown error"
            }
            //Do not display this message - part2 only
            //Toast.makeText(this, "Sign In failed: $errorMessage", Toast.LENGTH_SHORT).show()
            //Temp Allow access
            navigateToMainActivity()
        }
    }


    // Navigates to the main activity and clears this activity from the back stack
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Processes email and password sign-in
    private fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        navigateToMainActivity()
                    } else {
                        //Do not display this message - part2 only
                        //Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        //Temp ALlow
                        navigateToMainActivity()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigates to the signup activity
    private fun navigateToSignup() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}

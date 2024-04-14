package com.chronometron.test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            Log.d("SignupActivity", "Attempting to register user with email: $email")
            createUser(email, password)
        }
    }

    private fun createUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("SignupActivity", "Signup successful for email: $email")
                        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                        finish() // Optionally close this activity
                    } else {
                        Log.e("SignupActivity", "Signup failed for email: $email", task.exception)
                        Toast.makeText(this, "Signup failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Log.w("SignupActivity", "Email or password fields were empty")
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}

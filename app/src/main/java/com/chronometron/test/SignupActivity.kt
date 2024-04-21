package com.chronometron.test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

// SignupActivity manages user registration
class SignupActivity : AppCompatActivity() {

    // Firebase Authentication instance to handle user authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)  // Set the layout for the activity

        // Initialize Firebase Authentication to manage user accounts
        auth = FirebaseAuth.getInstance()

        // Find the EditText fields for email and password input by the user
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        // Find the signup button
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // Set up a click listener for the signup button
        signUpButton.setOnClickListener {
            // Retrieve the text entered in the email and password EditText fields
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Log attempt to register user
            Log.d("SignupActivity", "Attempting to register user with email: $email")
            // Call createUser method to initiate the signup process
            createUser(email, password)
        }
    }

    // Method to create a new user using email and password
    private fun createUser(email: String, password: String) {
        // Check if the email and password fields are not empty
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Use FirebaseAuth to create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Log and toast message if the signup is successful
                        Log.d("SignupActivity", "Signup successful for email: $email")
                        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                        // Optionally close the activity
                        finish()
                    } else {
                        // Log and display errors if the signup process fails
                        Log.e("SignupActivity", "Signup failed for email: $email", task.exception)
                        Toast.makeText(this, "Signup failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            // Log and warn the user if email or password fields are empty
            Log.w("SignupActivity", "Email or password fields were empty")
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}

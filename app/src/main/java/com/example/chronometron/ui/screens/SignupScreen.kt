package com.example.chronometron.ui.screens

import android.content.Context
import android.widget.Toast // Import Toast class

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignupScreen() {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSigningUp by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            enabled = !isSigningUp
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            enabled = !isSigningUp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isSigningUp = true
                createUser(email, password, auth, context) {
                    isSigningUp = false
                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty() && !isSigningUp
        ) {
            Text("Sign Up")
        }
    }
}

fun createUser(email: String, password: String, auth: FirebaseAuth, context: Context, onResult: () -> Unit) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            onResult()
            if (task.isSuccessful) {
                Toast.makeText(context, "Signup successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Signup failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    } else {
        Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
    }
}

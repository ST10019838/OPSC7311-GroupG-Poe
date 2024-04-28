@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chronometron.ui.screens

// Importing necessary Android and Jetpack Compose components for UI
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle


// Importing Navigation component for handling navigation
import androidx.navigation.NavController
import com.example.chronometron.CredentialsManager

// Firebase authentication for user management
import com.google.firebase.auth.FirebaseAuth

// Resource file imports for drawable resources
import com.example.chronometron.R

/**
 * Composable function to display the sign-up screen.
 * It leverages FirebaseAuth for authentication processes and NavController for navigation.
 *
 * @param auth FirebaseAuth instance for handling user authentication.
 * @param navController NavController instance for navigating between screens.
 */
@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current // Context access for displaying Toast messages
    var email by remember { mutableStateOf("") } // Mutable state for handling email input
    var password by remember { mutableStateOf("") } // Mutable state for handling password input
    var confirmPassword by remember { mutableStateOf("") } // Mutable state for handling confirm password input

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController.navigate("loginScreen") } // Finishes the activity to navigate back
                    .size(36.dp),
                tint = Color.White // Set icon color to white
            )

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White, // Set text color to white
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 96.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_chronometron_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(width = 233.dp, height = 150.dp)
                .padding(top = 48.dp)
        )
        Spacer(Modifier.height(48.dp))

        // Invoke the custom-defined composable for email, password, and confirm password input fields
        EmailPasswordFields(
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onConfirmPasswordChange = { confirmPassword = it }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword && password.isNotEmpty()) {
                    CredentialsManager.addCredential(email, password) // Store credentials locally
                    Toast.makeText(context, "Signup successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate("loginScreen") // Navigate to loginScreen
                } else if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Passwords cannot be empty", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B4D8))
        ) {
            Text("Sign Up")
        }

        Text(
            text = "Cancel",
            color = Color.White, // Set text color to white
            modifier = Modifier
                .clickable { navController.navigate("loginScreen") } // Finish the activity to cancel the operation
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontSize = 18.sp
        )
    }
}

/**
 * Custom composable function to generate email, password, and confirm password input fields.
 * Includes custom text, border, and hint color settings.
 */
@Composable
fun EmailPasswordFields(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit
) {
    // Define common TextField colors
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        containerColor = Color.Transparent,  // Sets the background color of the TextField
        focusedBorderColor = Color.White,    // Color of the border when TextField is focused
        unfocusedBorderColor = Color.White,  // Color of the border when TextField is not focused
        focusedLabelColor = Color.White,     // Color of the label when TextField is focused
        unfocusedLabelColor = Color.White    // Color of the label when TextField is unfocused
        // The textColor and placeholderColor are set via TextStyle below
    )

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email", color = Color.White) },
        textStyle = TextStyle(color = Color.White), // Setting the text color
        colors = textFieldColors

    )
    Spacer(Modifier.height(16.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password", color = Color.White) },
        textStyle = TextStyle(color = Color.White), // Setting the text color
        colors = textFieldColors,
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(Modifier.height(16.dp))
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        label = { Text("Confirm Password", color = Color.White) },
        textStyle = TextStyle(color = Color.White), // Setting the text color
        colors = textFieldColors,
        visualTransformation = PasswordVisualTransformation()
    )
}

/**
 * Function to handle user creation
 * It includes navigation to the main screen upon successful signup.
 */
fun createUser(email: String, password: String, auth: FirebaseAuth, context: android.content.Context, navController: NavController) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Signup successful! Please log in.", Toast.LENGTH_SHORT).show()
                navController.navigate("loginScreen")  // Navigate to login screen upon success
            } else {
                task.exception?.message?.let { errorMessage ->
                    Toast.makeText(context, "Signup failed: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
        }
    } else {
        Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
    }
}

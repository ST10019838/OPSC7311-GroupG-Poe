package com.example.chronometron.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chronometron.R

@Composable
fun SignUpScreen() {
    // Overall container for the sign up screen.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03045E)) // Dark blue color for background.
            .padding(16.dp)
    ) {
        // Column for aligning sign up components.
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title text for sign up screen.
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            // Logo display.
            Image(
                painter = painterResource(id = R.drawable.ic_chronometron_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(width = 233.dp, height = 150.dp)
                    .padding(top = 96.dp)
            )

            Spacer(modifier = Modifier.height(52.dp))

            // Email input field.
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Password input field.
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Confirm Password input field.
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Confirm Password") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Sign up button.
            Button(
                onClick = { /* Perform sign up operation */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00B4D8)), // Light blue button color.
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Cancel text with clickable modifier.
            Text(
                text = "Cancel",
                color = Color.White,
                modifier = Modifier.clickable(onClick = { /* Handle cancel operation */ })
            )
        }
    }
}

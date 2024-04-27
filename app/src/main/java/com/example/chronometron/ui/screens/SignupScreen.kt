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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03045E)) // Replace with your actual dark_blue color
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_chronometron_logo),
                contentDescription = "Chronometron Logo",
                modifier = Modifier
                    .size(width = 233.dp, height = 102.dp)
                    .padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(52.dp))

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

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Confirm Password") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(56.dp))

            Button(
                onClick = { /* Perform sign up */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00B4D8)), // Replace with your actual button color
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Cancel",
                color = Color.White,
                modifier = Modifier.clickable(onClick = { /* Handle cancel */ })
            )
        }
    }
}

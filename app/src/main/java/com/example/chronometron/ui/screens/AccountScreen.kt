// AccountScreen.kt
package com.example.chronometron.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chronometron.R
import com.example.chronometron.forms.SignUpForm
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.utils.onFormValueChange
import com.google.firebase.auth.FirebaseAuth

enum class Mode {
    SignUp, ForgotPassword
}

data class AccountScreen(
    val mode: Mode
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val form = SignUpForm()
        val auth = remember { FirebaseAuth.getInstance() }

        // Log the screen mode
        Log.d("AccountScreen", "Content called with mode: $mode")

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (mode == Mode.SignUp) "Sign Up" else "Forgot Password",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Arrow Back"
                            )
                        }
                    },
                )
            },

            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 25.dp)
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // Log the setup of UI elements
                    Log.d("AccountScreen", "UI elements set up")

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_chronometron_logo),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .aspectRatio(16f / 9f)
                        )

                        TextField(
                            value = form.email.state.value,
                            label = "Email",
                            isRequired = true,
                            onChange = { value ->
                                onFormValueChange(
                                    value = value,
                                    form = form,
                                    fieldState = form.email
                                )
                            },
                            hasError = form.email.hasError(),
                            errorText = form.email.errorText,
                            placeholderText = "Email"
                        )

                        var passwordVisible by remember { mutableStateOf(false) }
                        TextField(
                            value = form.password.state.value,
                            label = if (mode == Mode.SignUp) "Password" else "New Password",
                            isRequired = true,
                            onChange = { value ->
                                onFormValueChange(
                                    value = value,
                                    form = form,
                                    fieldState = form.password
                                )
                            },
                            hasError = form.password.hasError(),
                            errorText = form.password.errorText,
                            placeholderText = "Password",
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, description)
                                }
                            }
                        )

                        TextField(
                            value = form.passwordConfirmation.state.value,
                            label = "Confirm Password",
                            isRequired = true,
                            onChange = { value ->
                                onFormValueChange(
                                    value = value,
                                    form = form,
                                    fieldState = form.passwordConfirmation
                                )
                            },
                            hasError = form.passwordConfirmation.hasError(),
                            errorText = form.passwordConfirmation.errorText,
                            placeholderText = "Password",
                            visualTransformation =
                            if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = {
                                    passwordVisible = !passwordVisible
                                }) {
                                    Icon(imageVector = image, description)
                                }
                            }
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    var actionWasSuccessful by remember { mutableStateOf(true) }
                    Column {
                        if (!actionWasSuccessful) {
                            Text(
                                "Something went wrong, please try again",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = {
                                form.validate(true)
                                if (form.isValid) {
                                    // Log form validation
                                    Log.d("AccountScreen", "Form is valid, proceeding with ${if (mode == Mode.SignUp) "Sign Up" else "Reset Password"}")
                                    if (mode == Mode.SignUp) {
                                        createUser(
                                            email = form.email.state.value!!,
                                            password = form.password.state.value!!,
                                            auth = auth,
                                            context = context
                                        )
                                    } else {
                                        // Implement password reset functionality here
                                    }
                                } else {
                                    // Log form invalidation
                                    Log.d("AccountScreen", "Form is invalid")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (mode == Mode.SignUp) "Sign Up" else "Reset Password")
                        }

                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { navigator.pop() }
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            })
    }
}

fun createUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: android.content.Context
) {
    // Log the start of user creation
    Log.d("createUser", "createUser called with email: $email")
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Log successful signup
                    Log.d("createUser", "Signup successful")
                    Toast.makeText(context, "Signup successful! Please log in.", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = task.exception?.message
                    // Log signup failure
                    Log.e("createUser", "Signup failed: $errorMessage")
                    Toast.makeText(context, "Signup failed: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                // Log failure exception
                Log.e("createUser", "Signup failed with exception: ${exception.message}")
                Toast.makeText(context, "Signup failed: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    } else {
        // Log empty fields warning
        Log.w("createUser", "Email and password cannot be empty")
        Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
    }
}

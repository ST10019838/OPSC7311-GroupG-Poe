package com.example.chronometron.ui.screens

// Importing necessary Android and Jetpack Compose components for UI


// Importing Navigation component for handling navigation

// Firebase authentication for user management

// Resource file imports for drawable resources
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.chronometron.CredentialsManager
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
                )
                {
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

                                // Please provide localized description for accessibility services
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

                                // Please provide localized description for accessibility services
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

                                    if (mode == Mode.SignUp) {
                                        CredentialsManager.addCredentials(
                                            email = form.email.state.value!!,
                                            password = form.password.state.value!!
                                        )
                                    } else {
                                        actionWasSuccessful = CredentialsManager.updateCredentials(
                                            email = form.email.state.value!!,
                                            password = form.password.state.value!!
                                        )
                                    }

                                    if (actionWasSuccessful) {
                                        Toast.makeText(
                                            context,
                                            if (mode == Mode.SignUp) "Sign Up Successful"
                                            else "Password Reset Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navigator.pop()
                                    }
//                                    navigator.push(LandingScreen())
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


/**
 * Function to handle user creation
 * It includes navigation to the main screen upon successful signup.
 */
fun createUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: android.content.Context,
//    navController: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Signup successful! Please log in.", Toast.LENGTH_SHORT)
                    .show()
//                navController.navigate("loginScreen")  // Navigate to login screen upon success
            } else {
                task.exception?.message?.let { errorMessage ->
                    Toast.makeText(context, "Signup failed: $errorMessage", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    } else {
        Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
    }
}
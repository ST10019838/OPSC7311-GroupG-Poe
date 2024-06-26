package com.example.chronometron.ui.screens

//import com.example.chronometron.api.getCategories
// Basic layout building blocks in Jetpack Compose

// Material Design 3 components for modern, beautiful UIs

// Compose runtime for managing state within composables

// Basic UI modifiers and units for spacing, sizing, and alignment

// UI elements for images and advanced layout configurations

// Core Material Design components from older Material library

// Material Design icons and runtime tools for handling dynamic UI elements

// Alignment tools for positioning elements within composables

// Resource handling for images, supporting drawable resources

// Input handling for text fields, including password masking

// Alias for Modifier to avoid ambiguity with other Modifier imports

// Icons for visibility control in password fields
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff

// Tooling support for previewing Compose UIs within the IDE

// Resource class generated by Android for accessing app resources

//Colours and layout
//import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chronometron.Firebase.Authentication.signIn
import com.example.chronometron.R
import com.example.chronometron.forms.LoginForm
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.utils.onFormValueChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginScreen(
//    val onLogin: (String, String) -> Unit,
//    val onGoogleSignIn: () -> Unit = {},
//    val onGithubSignIn: () -> Unit = {},
//    val onSignUp: () -> Unit = {}
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val form = LoginForm()
        val context = LocalContext.current


        Scaffold(content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 25.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            )
            {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chronometron_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .aspectRatio(16f / 9f)
                    )

                    // Social media login options
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = {} /*onGoogleSignIn*/,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = RoundedCornerShape(15),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            enabled = false
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google_logo),
                                contentDescription = "Google Login",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Google", style = MaterialTheme.typography.titleMedium)
                        }

                        OutlinedButton(
                            onClick = {} /*onGithubSignIn*/,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = RoundedCornerShape(15),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            enabled = false
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_github_logo),
                                contentDescription = "GitHub Login",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("GitHub", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .fillMaxWidth()
                                .weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "or",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .fillMaxWidth()
                                .weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

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

                    Spacer(Modifier.height(5.dp))

                    var passwordVisible by remember { mutableStateOf(false) }
                    TextField(
                        value = form.password.state.value,
                        label = "Password",
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

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    )
                }

                var isLoading by remember { mutableStateOf(false) }
                var showErrorMessage by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()

                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    if (showErrorMessage) {
                        Text(
                            "Incorrect Email or Password",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Button(
                        enabled = !isLoading,
                        onClick = {
                            isLoading = true

                            form.validate(true)

                            if (form.isValid) {
                                signIn(
                                    email = form.email.state.value!!,
                                    password = form.password.state.value!!,
                                    navigator = navigator,
                                    showErrorMessage = {
                                        showErrorMessage = true
                                    }
                                )
                            }

                            scope.launch {
                                delay(750)
                                isLoading = false
                            }
                        },
//                        onClick = { navigator.push(LandingScreen()) },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {

                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        }

                        Text("Login")
                    }

                    TextButton(
                        onClick = { navigator.push(AccountScreen(mode = Mode.ForgotPassword)) }, /*onSignUp*/
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            "Forgot Password?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    TextButton(
                        onClick = { navigator.push(AccountScreen(mode = Mode.SignUp)) },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            "Sign Up",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        )
    }
}


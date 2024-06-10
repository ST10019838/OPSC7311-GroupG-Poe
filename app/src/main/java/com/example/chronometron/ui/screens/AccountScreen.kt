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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.chronometron.Firebase.Authentication.createAccount
import com.example.chronometron.Firebase.Authentication.resetPassword
import com.example.chronometron.R
import com.example.chronometron.forms.AccountManagementForm
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.utils.onFormValueChange
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        val form = AccountManagementForm(mode)

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
                    verticalArrangement =
                    if (mode == Mode.SignUp) Arrangement.SpaceAround
                    else Arrangement.spacedBy(15.dp)
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

                        if (mode == Mode.SignUp) {
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

                    }

                    Spacer(Modifier.height(20.dp))

                    var isLoading by remember { mutableStateOf(false) }
                    var showErrorMessage by remember { mutableStateOf(false) }
                    var showSuccessMessage by remember { mutableStateOf(false) }
                    val scope = rememberCoroutineScope()

                    Column {
                        if (showErrorMessage) {
                            Text(
                                "Something went wrong, please try again",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        } else if (showSuccessMessage) {
                            Text(
                                "Password reset email sent!",
                                color = Color.Green,
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
                                    if (mode == Mode.SignUp) {
                                        createAccount(
                                            email = form.email.state.value!!,
                                            password = form.password.state.value!!,
                                            navigator = navigator,
                                            showErrorMessage = {
                                                showErrorMessage = true
                                            }
                                        )
                                    } else {
                                        resetPassword(
                                            email = form.email.state.value!!,
                                            navigator = navigator,
                                            showErrorMessage = {
                                                showErrorMessage = true
                                            },
                                            showSuccessMessage = {
                                                showSuccessMessage = true
                                            }
                                        )
                                    }
                                }

                                scope.launch {
                                    delay(750)
                                    isLoading = false
                                }

                            },
                            modifier = Modifier.fillMaxWidth()
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
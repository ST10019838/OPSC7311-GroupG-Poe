package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Colors
import androidx.compose.material.DropdownMenu
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.chronometron.Firebase.Authentication.deleteAccount
import com.example.chronometron.Firebase.Authentication.signOut
import com.example.chronometron.ui.screens.LoginScreen

@Composable
fun AccountAvatar() {
    var openMenu by remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow
    var openDialog by remember { mutableStateOf(false) }


//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.TopStart)
//    ) {
    IconButton(onClick = { openMenu = true }) {
        Icon(
            Icons.Default.PersonOutline,
            contentDescription = "Account Avatar"
        )
    }
    DropdownMenu(
        expanded = openMenu,
        onDismissRequest = { openMenu = false }
    ) {
        DropdownMenuItem(
            text = { Text("Sign Out", color = MaterialTheme.colorScheme.primary) },
            onClick = {
                openMenu = false
                signOut()
                navigator.parent?.push(LoginScreen())
            },
            leadingIcon = {
                Icon(
                    Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = "Logout",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
        DropdownMenuItem(
            text = { Text("Delete Account", color = MaterialTheme.colorScheme.error) },
            onClick = {
                openDialog = true
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Account",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
//    }


    if (openDialog) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Account",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(text = "Delete Account")
            },
            text = {
                Row {
                    Text("Are you sure you want to delete your account?")
                }
            },
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        openMenu = false
                        deleteAccount()
                        navigator.parent?.push(LoginScreen())
                    }
                ) {
                    Text("Confirm",  color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss", color = Color.Green)
                }
            }
        )
    }

}
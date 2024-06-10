package com.example.chronometron.Firebase

import android.util.Log
import cafe.adriel.voyager.navigator.Navigator
import com.example.chronometron.Firebase.Database.initializeDatabase
import com.example.chronometron.api.removeUser
import com.example.chronometron.ui.screens.LandingScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

object Authentication {
    lateinit var auth: FirebaseAuth

    fun createAccount(
        email: String,
        password: String,
        navigator: Navigator,
        showErrorMessage: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Account Creation", "createUserWithEmail:success")

                    if (auth.currentUser != null) {
                        initializeDatabase()
                        navigator.push(LandingScreen())
                    } else {
                        showErrorMessage()
                    }

                } else {
                    Log.w("Account Creation", "createUserWithEmail:failure", task.exception)
                    showErrorMessage()
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        navigator: Navigator?,
        showErrorMessage: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Sign In", "signInWithEmail:success")

                    if (auth.currentUser != null) {
                        initializeDatabase()
                        navigator?.push(LandingScreen())
                    } else {
                        showErrorMessage()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign In", "signInWithEmail:failure", task.exception)

                    showErrorMessage()
                }
            }
//        return signInSuccessful
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    // The following function was adapted from Youtube
    // Author: tutorialsEU
    // Link: https://www.youtube.com/watch?v=nVhPqPpgndM
    fun resetPassword(
        email: String,
        navigator: Navigator,
        showErrorMessage: () -> Unit,
        showSuccessMessage: () -> Unit
    ) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Reset Password", "signInWithEmail:success")


                showSuccessMessage()
//                if (auth.currentUser != null) {
//                    navigator.pop()
//                } else {
//                    showErrorMessage()
//                }

            } else {
                // If sign in fails, display a message to the user.
                Log.w("Sign In", "signInWithEmail:failure", task.exception)

                showErrorMessage()
            }
        }
    }


    fun deleteAccount(){
        removeUser()
        auth.currentUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("User deletion", "User account deleted.")
                }
            }

    }
}


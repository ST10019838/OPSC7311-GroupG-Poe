package com.example.chronometron

/**
 * Object to manage user credentials. Stores email and password pairs.
 */
object CredentialsManager {
    private val credentials = mutableMapOf<String, String>()

    fun addCredential(email: String, password: String) {
        // Normalize email to lower case and trim spaces
        val normalizedEmail = email.trim().lowercase()
        credentials[normalizedEmail] = password
    }

    fun validateCredential(email: String, password: String): Boolean {
        val normalizedEmail = email.trim().lowercase()
        return credentials[normalizedEmail] == password
    }
}


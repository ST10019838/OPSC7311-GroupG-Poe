package com.example.chronometron

//import androidx.room.Room
//import com.example.chronometron.db.LocalDatabase
//import com.example.chronometron.ui.viewModels.TestViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.HiltAndroidApp
//import com.example.chronometron.ui.screens.SignUpScreen
//import com.example.chronometron.db.Database.database
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.chronometron.Firebase.Authentication.auth
import com.example.chronometron.Firebase.Authentication.signOut
import com.example.chronometron.ui.screens.LandingScreen
import com.example.chronometron.ui.screens.LoginScreen
import com.example.chronometron.ui.theme.ChronoMetronTheme
import com.example.chronometron.ui.viewModels.SessionState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var auth: FirebaseAuth
//
//    // Activity result launcher to handle Google Sign-In result
//    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        Log.d("LoginActivity", "Google sign-in result code: ${result.resultCode}, Data: ${result.data}")
//        if (result.resultCode == 0) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            handleSignInResult(task)
//        } else {
//            Log.e("LoginActivity", "Sign-in failed with result code: ${result.resultCode}, Intent data: ${result.data}")
////            Toast.makeText(this, "Sign-In cancelled or failed. Result Code: ${result.resultCode}, Data: ${result.data}", Toast.LENGTH_LONG).show()
//        }
//    }


    // use dependency injection
//    private val viewModel by viewModel<ContactViewModel>(
//        factoryProducer = {
//            object: ViewModelProvider.Factory{
//                override fun <T: ViewModel?> create(modelClass: Class<T>): T{
//                    return ContactViewModel(db.dao).as T
//                }
//            }
//        }
//    )

    //    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The following if statement was taken from Youtube.com
        // Author: Philipp Lackner
        // Link: https://www.youtube.com/watch?v=12_iKwGIP64

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        auth = Firebase.auth

//        signOut()
//        Firebase.auth.signOut()


        setContent {
            val isDarkMode by SessionState.isDarkMode.collectAsStateWithLifecycle()

            ChronoMetronTheme(darkTheme = isDarkMode) {
//                TabNavigator(TimeEntriesTab) { tabNavigator ->
//                    ScreenLayout(tabNavigator)
//                }

//                if (currentUser != null){
//                    Text("USER NOT NULL " + currentUser.uid )
//
//                } else{
//                    Text("USEr NULL")
//                }

                Navigator(
                    if (auth.currentUser != null)
                        LandingScreen()
                    else
                        LoginScreen(
//                    onLogin = { email, password -> signInWithEmail(email, password) },
//                    onGoogleSignIn = { signInWithGoogle() },
//                    onGithubSignIn = { /* Implement GitHub login if necessary */ },
//                    onSignUp = { /* navController.navigate("signupScreen") */}
                        )
                ) {
                    SlideTransition(it)
                }
            }
        }
    }


    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }


//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        googleSignInLauncher.launch(signInIntent)
//    }


//    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
//        try {
//            val account = task.getResult(ApiException::class.java)
////            Toast.makeText(this, "Sign In successful!", Toast.LENGTH_SHORT).show()
////            navigateToMainActivity()
//        } catch (e: ApiException) {
//            Log.e("LoginActivity", "Sign In failed with error code: ${e.statusCode} - ${e.message}")
////            navigateToMainActivity()  // For debugging, remove or handle properly in production
//        }
//    }

//    private fun signInWithEmail(email: String, password: String) {
//        if (CredentialsManager.validateCredential(email, password)) {
//            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
////            navigateToMainActivity()
//        } else {
//            Toast.makeText(this, "Login failed: Invalid credentials.", Toast.LENGTH_SHORT).show()
//        }
//
//    }

//    @Composable
//    private fun navigateToMainActivity() {
//        val navigator = LocalNavigator.currentOrThrow
//        navigator.push(LandingScreen())
//    }
//
//    @Composable
//    private fun navigateToSignup() {
//        val navigator = LocalNavigator.currentOrThrow
//        navigator.push(SignUpScreen())
//    }
}

data class User(val username: String? = null, val email: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}



package com.chronometron.test

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chronometron.test.databinding.ActivityMainBinding

// The MainActivity class is the entry point for the app's main screen.
class MainActivity : AppCompatActivity() {

    // Declare the binding object to access views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Set the content view of the activity

        // Retrieve the BottomNavigationView from the binding object
        val navView: BottomNavigationView = binding.navView

        // Find the navigation controller associated with the NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Define top level destinations to configure the app bar for navigation
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_customizer,  // ID for the customizer navigation item
                R.id.navigation_time_entries,  // ID for the time entries navigation item
                R.id.navigation_statistics  // ID for the statistics navigation item
            )
        )

        // Set up the ActionBar with the NavController and AppBarConfiguration
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Connect the BottomNavigationView with the NavController
        navView.setupWithNavController(navController)
    }
}

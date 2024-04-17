package com.example.chronometron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.screens.CustomiserScreen
import com.example.chronometron.ui.theme.ChronoMetronTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            ChronoMetronTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CustomiserScreen()

                }
            }
        }
    }
}

@Composable
fun BackendTesting(){
    var data: List</* put type here */> by rememberSaveable { mutableStateOf(/*place fetch function here*/) }

    Column {
        Button(onClick = { /* Test functionality of whatever */ }) {
            Text("Click Me")
        }

        LazyColumn {
            items(data){
                Text(" ${/* item property*/} ${/* item property*/} ${/* item property*/}")
            }
        }
    }
}

// Used to determine if data should be re-fetched
//private data class SessionState(
//
//
//)



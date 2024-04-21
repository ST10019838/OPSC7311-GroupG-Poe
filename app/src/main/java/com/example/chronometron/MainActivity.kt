package com.example.chronometron

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.chronometron.ui.screens.CustomiserTab
import com.example.chronometron.ui.screens.StatisticsTab
import com.example.chronometron.ui.screens.TimeEntriesTab
import com.example.chronometron.ui.theme.ChronoMetronTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            ChronoMetronTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    CustomiserScreen()
//
//                }

//                Navigator(TimeEntriesScreen()) { navigator ->
//
//                    ScreenLayout(navigator = navigator) {
//                        SlideTransition(navigator = navigator)
//                    }
//                }

                TabNavigator(TimeEntriesTab) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text(it.current.options.title)
                                }
                            )
                        },
                        floatingActionButton = {
//                            if (it.current.) {
//                                FloatingActionButton(onClick = {}) {
//                                    Icon(Icons.Default.Add, contentDescription = "Add")
//                                }
//                            }
                        },
                        bottomBar = {
                            NavigationBar {
                                TabNavigationItem(CustomiserTab)
                                TabNavigationItem(TimeEntriesTab)
                                TabNavigationItem(StatisticsTab)
                            }
                        },
                        content = {
                            Box(modifier = Modifier.padding(it)) {
                                CurrentTab()
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab

    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                tab.options.icon!!,
                contentDescription = tab.options.title,
                modifier = Modifier.alpha(if (!isSelected) 0.5f else 1f)
            )
        },
        label = {
            Text(
                text = tab.options.title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = if (isSelected) TextUnit.Unspecified else 10.sp,
                modifier = Modifier.alpha(if (!isSelected) 0.5f else 1f)
            )
        }
    )
}

//@Composable
//fun BackendTesting(){
//    var data: List</* put type here */> by rememberSaveable { mutableStateOf(/*place fetch function here*/) }
//
//    Column {
//        Button(onClick = { /* Test functionality of whatever */ }) {
//            Text("Click Me")
//        }
//
//        LazyColumn {
//            items(data){
//                Text(" ${/* item property*/} ${/* item property*/} ${/* item property*/}")
//            }
//        }
//    }
//}

// Used to determine if data should be re-fetched
//private data class SessionState(
//
//
//)



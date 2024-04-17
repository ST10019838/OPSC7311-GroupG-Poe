package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chronometron.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLayout(displayFAB: Boolean = false, content: @Composable () -> Unit) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBarItems = listOf(
        NavItem(
            name = "Customiser",
            icon = painterResource(id = R.drawable.baseline_bar_chart_24)
        ),
        NavItem(
            name = "Timesheet Entries",
            icon = painterResource(id = R.drawable.baseline_bar_chart_24)
        ),
        NavItem(
            name = "Statistics",
            icon = painterResource(id = R.drawable.baseline_bar_chart_24)
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("${navBarItems[selectedItem].name}")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navBarItems.forEachIndexed { index, item ->
                    val isSelected = selectedItem == index

                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.name,
                                modifier = Modifier.alpha(if (!isSelected) 0.5f else 1f)
                            )
                        },
                        label = {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = if (isSelected) TextUnit.Unspecified else 10.sp,
                                modifier = Modifier.alpha(if (!isSelected) 0.5f else 1f)
                            )
                        },
                        selected = isSelected,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        floatingActionButton = {
            if (displayFAB) {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            content()
        }
    }


}

private data class NavItem(
    var name: String,
    var icon: Painter
)
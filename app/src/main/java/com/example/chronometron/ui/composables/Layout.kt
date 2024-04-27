package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.chronometron.ui.screens.TimeEntriesCreationScreen
import com.example.chronometron.ui.tabs.CustomiserTab
import com.example.chronometron.ui.tabs.StatisticsTab
import com.example.chronometron.ui.tabs.TimeEntriesTab
import com.example.chronometron.ui.viewModels.EntryCreationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout(tabNavigator: TabNavigator) {
    Scaffold(
        topBar =
        {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(tabNavigator.current.options.title)
                }
            )
        },
        bottomBar =
        {
            NavigationBar {
                TabNavigationItem(CustomiserTab)
                TabNavigationItem(TimeEntriesTab)
                TabNavigationItem(StatisticsTab)
            }
        },
        content =
        {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 25.dp, vertical = 15.dp)
            ) {
                CurrentTab()
            }
        }
    )

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
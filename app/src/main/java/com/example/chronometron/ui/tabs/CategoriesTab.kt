package com.example.chronometron.ui.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.ui.screens.CategoryHoursScreen

//import com.example.chronometron.ui.viewModels.TestViewModel
//import com.example.chronometron.ui.viewModels.UserSession

object CategoriesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Categories"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
       CategoryHoursScreen()
    }
}

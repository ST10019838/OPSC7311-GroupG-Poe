package com.example.chronometron.ui.tabs

//import com.example.chronometron.api.getCategories
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.ui.screens.CustomiserScreen


object CustomiserTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Customiser"
            val icon = rememberVectorPainter(Icons.Outlined.EditNote)

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
        CustomiserScreen()
    }
}


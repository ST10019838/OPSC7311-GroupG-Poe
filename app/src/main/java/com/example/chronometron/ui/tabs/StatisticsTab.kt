package com.example.chronometron.ui.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.R


object StatisticsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Statistics"
            // Load the custom drawable as an icon
            val iconPainter: Painter = painterResource(id = R.drawable.ic_statistics_white_24dp)

            // Directly return TabOptions without using remember since it's not needed here
            return TabOptions(
                index = 2u,
                title = title,
                icon = iconPainter
            )
        }

    @Composable
    override fun Content() {
        // Implementation of the Statistics screen content
        Text("Stats Screen")
    }
}
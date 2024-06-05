package com.example.chronometron.ui.tabs


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.ui.composables.DailyHoursGraph
import com.example.chronometron.ui.composables.GoalAdherenceRating
import com.example.chronometron.ui.screens.DailyHoursScreen
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.hoursToFloat

object DailyHoursTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val title = "Daily Hours"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 1u, title = title, icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        DailyHoursScreen()
    }
}






package com.example.chronometron.ui.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.ui.composables.SelectablePeriodSearch
import com.example.chronometron.ui.viewModels.UserSession

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
        val categoryHours by UserSession.categoryHours.collectAsState()
        val selectablePeriod by UserSession.selectedPeriod.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SelectablePeriodSearch(
                value = selectablePeriod,
                onSelectionChange = {
                    UserSession.onSelectedPeriodChange(
                        it?.fromDate,
                        it?.toDate
                    )
                },
                isOpen = false
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
            ) {
                Text("Category", style = MaterialTheme.typography.labelLarge)

                Text("Total Time", style = MaterialTheme.typography.labelLarge)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {

                items(categoryHours.toList()) {
                    CategoryHoursListItem(it)
                }
            }


        }
    }
}

@Composable
private fun CategoryHoursListItem(item: Pair<Category, Hours>) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(item.first.name)

            Text("${item.second.hours}h ${item.second.minutes}m")
        }
    }

}
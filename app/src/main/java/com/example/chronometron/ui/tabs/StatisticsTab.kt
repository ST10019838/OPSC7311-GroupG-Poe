package com.example.chronometron.ui.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.BarChart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions


object StatisticsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Statistics"
            val icon = rememberVectorPainter(Icons.Sharp.BarChart)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(CategoriesTab) { tabNavigator ->
            Scaffold(
                bottomBar =
                {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            thickness = 2.dp, modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(100))
                        )

                        SecondaryTabRow(
                            selectedTabIndex = tabNavigator.current.options.index.toInt(),
                            divider = {},
                            modifier = Modifier.fillMaxWidth(),
                        ) {

                            Tab(
                                selected = tabNavigator.current == CategoriesTab,
                                onClick = { tabNavigator.current = CategoriesTab },
                                text = {
                                    Text(
                                        text = CategoriesTab.options.title,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Tab(
                                selected = tabNavigator.current == DailyHoursTab,
                                onClick = { tabNavigator.current = DailyHoursTab },
                                text = {
                                    Text(
                                        text = DailyHoursTab.options.title,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                },
                content =
                {
                    Box(
                        modifier = Modifier.padding(it)
                    ) {
                        CurrentTab()
                    }
                }
            )
        }
    }
}
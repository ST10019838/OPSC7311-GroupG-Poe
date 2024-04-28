package com.example.chronometron.ui.tabs

//import com.example.chronometron.api.getCategories
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.R
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.CollapsibleSection
import com.example.chronometron.ui.composables.TimeSelector


object CustomiserTab : Tab {

    override val options: TabOptions
    @Composable
    get() {
        val title = "Customiser"
        val iconPainter: Painter = painterResource(id = R.drawable.ic_customizer_white_24dp)

        return TabOptions(
            index = 0u,
            title = title,
            icon = iconPainter
        )
    }

    @Composable
    override fun Content() {

        //   Daily goal Section

        //   Categories Section
//
//   Other customizables

        LazyColumn {
            item {
                Goals()

                Categories()
            }
        }


    }
}

//@Composable


@Composable
private fun Goals() {
    var minimumGoal by remember { mutableStateOf<Hours>(FullHours(12, 43)) }
    var maximumGoal by remember { mutableStateOf<Hours>(FullHours(12, 43)) }

    fun saveMinimumGoal() {

    }

    fun saveMaximumGoal() {

    }

    CollapsibleSection(heading = "Goals") {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
//            TimeSelector(title = "Minimum Goal:", value = minimumGoal, onValueChange = {
//                minimumGoal = it
//            }, action = {
//                saveMinimumGoal()
//            }, actionIcon = {
//                Icon(
//                    Icons.Filled.Add,
//                    contentDescription = "Localized description",
//                    Modifier.size(InputChipDefaults.AvatarSize)
//                )
//            })
//
//            TimeSelector(title = "Maximum Goal:", value = maximumGoal, onValueChange = {
//                maximumGoal = it
//            }, action = {
//                saveMaximumGoal()
//            }, actionIcon = {
//                Icon(
//                    Icons.Filled.Add,
//                    contentDescription = "Localized description",
//                    Modifier.size(InputChipDefaults.AvatarSize)
//                )
//            })
        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Categories() {
    var categories by rememberSaveable { mutableStateOf(listOf<String>()) }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }


    CollapsibleSection(heading = "Categories") {
        Column {
            // Try optimize by making it lazy load items (use ContextualFlowRow)
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                categories.forEachIndexed { index, item ->
                    CategoryChip(
                        item,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        confirmationAction = {
                            categories = categories.minusElement(item)
                        })
                }


                Button(onClick = {
//                        categories = categories.plus("text")

                    isDialogOpen = true
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add Category")
                }
            }
        }

        var text by remember { mutableStateOf<String>("") }

        fun dismissDialog() {
            text = ""
            isDialogOpen = false
        }

        if (isDialogOpen) {
            AlertDialog(
//            icon = {
//                Icon(icon, contentDescription = "Example Icon")
//            },
                title = {
                    Text(text = "Create Category")
                },
                text = {


                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Category Name") }
                    )
                },
                onDismissRequest = {
                    dismissDialog()
                },
                confirmButton = {
                    Button(
                        onClick = {
                            categories = categories.plus(text)
                            dismissDialog()
                        },

                        contentPadding = PaddingValues(horizontal = 40.dp)
                    ) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            dismissDialog()
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

    }
}

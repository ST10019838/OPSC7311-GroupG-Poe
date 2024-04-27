package com.example.chronometron.ui.tabs

//import com.example.chronometron.api.getCategories
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.types.Category
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.CollapsibleSection
import com.example.chronometron.ui.composables.TimeSelector
import com.example.chronometron.ui.forms.GoalSettingForm
import com.example.chronometron.ui.viewModels.UserSession
import java.util.UUID


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

        //   Daily goal Section

        //   Categories Section
//
//   Other customizables

        LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
            item {
                Goals()
            }

            item {
                Categories()
            }
        }


    }
}

//@Composable


@Composable
private fun Goals() {
    val form = GoalSettingForm()

    CollapsibleSection(heading = "Daily Goals") {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
            TimeSelector(
                label = "Minimum Goal:",
                form = form,
                fieldState = form.minimumGoal,
                onValueChange = {
                    UserSession.updateMinimumGoal(it)
                }
            ).Field()

            TimeSelector(
                label = "Maximum Goal:",
                form = form,
                fieldState = form.maximumGoal,
                onValueChange = {
                    UserSession.updateMaximumGoal(it)
                }
            ).Field()

        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Categories() {
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val categories = UserSession.categories

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
                        item.name,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        confirmationAction = {
                            UserSession.removeCategory(item)
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
                            UserSession.addCategory(Category(id = UUID.randomUUID(), name = text))
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

package com.example.chronometron.ui.screens

//import com.example.chronometron.api.getCategories
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.types.Goal
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.GoalInput


//

object CustomiserTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Customiser"
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

        //   Daily goal Section

        //   Categories Section
//
//   Other customizables

        Column {
            Goals()

            Categories()
        }

//        }

    }
}

//@Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Goals() {
    var isSectionOpen by rememberSaveable { mutableStateOf(true) }
//    var isSectionOpen by rememberSaveable { mutableStateOf(true) }

    var minimumGoal = Goal(0, 0)


    val (value, setValue) = remember { mutableStateOf(0) }
    var minimumMinutes by remember { mutableStateOf(0) }

    Column {

        Text("Goals: ", modifier = Modifier
            .clickable {
                isSectionOpen = !isSectionOpen
            }
            .fillMaxWidth(),
            textAlign = TextAlign.Center)

        AnimatedVisibility(visible = isSectionOpen) {
            Row {
                GoalInput(state = value, changeState = setValue)
            }

        }

    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Categories() {
    var categories by rememberSaveable { mutableStateOf(listOf("Text 1")) }
    var isSectionOpen by rememberSaveable { mutableStateOf(true) }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }


    Column {

        Text("Categories: ", modifier = Modifier
            .clickable {
                isSectionOpen = !isSectionOpen
            }
            .fillMaxWidth(),
            textAlign = TextAlign.Center)

        AnimatedVisibility(visible = isSectionOpen) {
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

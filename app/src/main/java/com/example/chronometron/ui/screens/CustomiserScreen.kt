package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chronometron.forms.GoalSetterForm
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.CategoryCreationDialog
import com.example.chronometron.ui.composables.CollapsibleSection
import com.example.chronometron.ui.composables.formFields.TimeSelector
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.onFormValueChange

/**
 * Customiser screen containing the different sections for goals, categories, and theme settings.
 */
@Composable
fun CustomiserScreen() {
    // LazyColumn allows scrolling of its content when overflow occurs
    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
        // Each item in LazyColumn can be scrolled independently
        item {
            Goals()
        }
        item {
            Categories()
        }
        item {
            Colours()
        }
    }
}

/**
 * Composable function to display the Goals section in the CustomiserScreen.
 * This section allows users to set their daily minimum and maximum goals.
 */
@Composable
private fun Goals() {
    // Collecting state values from ViewModel
    val minGoal by UserSession.minimumGoal.collectAsStateWithLifecycle()
    val maxGoal by UserSession.maximumGoal.collectAsStateWithLifecycle()

    // Initializing the form with state values
    var form = GoalSetterForm(minimumGoal = minGoal, maximumGoal = maxGoal)
    form.validate(true)

    // Collapsible section for displaying goal settings
    CollapsibleSection(
        heading = "Daily Goals",
        icon = { Icon(Icons.Default.TaskAlt, contentDescription = "Goals") }) {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
            // Time selector for minimum goal
            TimeSelector(
                label = "Minimum Goal",
                value = form.minGoal.state.value,
                onChange = {
                    onFormValueChange(
                        value = it,
                        form = form,
                        fieldState = form.minGoal
                    )
                    UserSession.updateMinimumGoal(it)
                },
                hasError = form.minGoal.hasError(),
                errorText = form.minGoal.errorText,
            )

            // Time selector for maximum goal
            TimeSelector(
                label = "Maximum Goal",
                value = form.maxGoal.state.value,
                onChange = {
                    onFormValueChange(
                        value = it,
                        form = form,
                        fieldState = form.maxGoal
                    )
                    if (form.isValid) {
                        UserSession.updateMaximumGoal(it)
                    }
                },
                hasError = form.maxGoal.hasError(),
                errorText = form.maxGoal.errorText,
            )
        }
    }
}

/**
 * Composable function to display the Categories section in the CustomiserScreen.
 * This section allows users to manage their categories by adding or removing them.
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Categories() {
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val categories by UserSession.categories.collectAsStateWithLifecycle()

    // Collapsible section for displaying categories
    CollapsibleSection(
        heading = "Categories",
        icon = { Icon(Icons.Default.Category, contentDescription = "Categories") }) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Conditional UI based on the availability of categories
            if (categories.isEmpty()) {
                Text(
                    "No Categories Created",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // FlowRow to display categories in a horizontal flow layout
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEachIndexed { _, item ->
                    CategoryChip(
                        item.name ?: "Unnamed Category", // Ensure non-nullable String
                        modifier = Modifier.padding(horizontal = 5.dp),
                        confirmationAction = {
                            UserSession.removeCategory(item)
                        })
                }

                // Button to add a new category
                Button(
                    onClick = { isDialogOpen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Category")
                }
            }
        }

        // Dialog for category creation
        if (isDialogOpen) {
            CategoryCreationDialog(onDismiss = {
                isDialogOpen = false
            })
        }
    }
}

/**
 * Composable function to display the Colours section in the CustomiserScreen.
 * This section allows users to toggle between light and dark mode.
 */
@Composable
private fun Colours() {
    val isDarkMode by UserSession.isDarkMode.collectAsStateWithLifecycle()

    // Collapsible section for displaying theme settings
    CollapsibleSection(
        heading = "Theme",
        icon = { Icon(Icons.Outlined.ColorLens, contentDescription = "Colours") }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Dark Mode:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Switch to toggle dark mode
            Switch(
                modifier = Modifier.semantics { contentDescription = "Dark Mode Switch" },
                checked = isDarkMode,
                onCheckedChange = { UserSession.toggleIsDarkMode(it) })
        }
    }
}

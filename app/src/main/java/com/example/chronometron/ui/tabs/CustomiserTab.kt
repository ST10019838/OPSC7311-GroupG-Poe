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
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.chronometron.types.Goals
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.CategoryCreationDialog
import com.example.chronometron.ui.composables.CollapsibleSection
import com.example.chronometron.ui.composables.formFields.TimeSelector
import com.example.chronometron.ui.screens.CustomiserScreen
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
        CustomiserScreen()
    }
}


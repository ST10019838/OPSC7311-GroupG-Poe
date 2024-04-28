package com.example.chronometron.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.forms.EntryCreationForm
import java.util.UUID

object UserSession : ViewModel() {
    var timeEntries by mutableStateOf<List<TimeEntry>>(emptyList())


    var categories by mutableStateOf<List<Category>>(emptyList())
        private set


    var minimumGoal: Hours by mutableStateOf(FullHours(0, 0))
        private set
    var maximumGoal: Hours by mutableStateOf(FullHours(0, 0))
        private set


    fun addTimeEntry(form: EntryCreationForm) {
        var startTimeValue = form.startTime.state.value!!
        var endTimeValue = form.endTime.state.value!!

        var newTimeEntry = TimeEntry(
            id = UUID.randomUUID(),
            description = form.description.state.value!!,
            date = form.date.state.value!!,
            startTime = startTimeValue,
            endTime = endTimeValue,
            duration = FullHours(
                hours = startTimeValue.hours - endTimeValue.hours,
                minutes = startTimeValue.minutes - endTimeValue.minutes
            ),
            category = form.category.state.value!!,
            photograph = form.photograph.state.value
        )

        timeEntries += newTimeEntry
    }

    fun updateMinimumGoal(goal: Hours) {
        minimumGoal = goal
    }

    fun updateMaximumGoal(goal: Hours) {
        maximumGoal = goal
    }

    fun addCategory(category: Category) {
//        categories = categories.plus(text)
        categories += category
    }

    fun removeCategory(category: Category) {
        categories = categories.minusElement(category)
    }
//    private val _timeEntries = MutableStateFlow(GameUiState())
//    val uiState: StateFlow<GameUiState> = _timeEntries.asStateFlow()

}
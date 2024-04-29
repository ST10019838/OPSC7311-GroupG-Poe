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
import java.util.Date
import java.util.UUID

object UserSession : ViewModel() {
    var timeEntries by mutableStateOf<List<TimeEntry>>(emptyList())

    fun getListOfTimeEntryDates(): Map<Date, MutableList<Int>> {
//        var listOfDates = mutableListOf<Date>()

        val datesAndEntriesMap = mutableMapOf<Date, MutableList<Int>>()
        val sortedEntries = timeEntries.sortedByDescending { it.date }

        sortedEntries.forEachIndexed { index, entry ->

            if (datesAndEntriesMap.containsKey(entry.date)) {
                datesAndEntriesMap[entry.date]?.add(index)
            } else {
                datesAndEntriesMap[entry.date] = mutableListOf(index)
            }

//            listOfDates += entry.date
        }

        return datesAndEntriesMap
    }

    init {
        val startTimeValue = FullHours(1, 0)
        val endTimeValue = FullHours(2, 0)

        var newTimeEntry = TimeEntry(
            id = UUID.randomUUID(),
            description = "This is the long description that will be used",
            date = Date(2024, 11, 1),
            startTime = startTimeValue,
            endTime = endTimeValue,
            duration = FullHours(
                hours = endTimeValue.hours - startTimeValue.hours,
                minutes = endTimeValue.minutes - startTimeValue.minutes
            ),
            category = Category(UUID.randomUUID(), "Fun"),
            photograph = null
        )

        timeEntries += newTimeEntry


        val startTimeValue2 = FullHours(1, 0)
        val endTimeValue2 = FullHours(2, 0)

        var newTimeEntry2 = TimeEntry(
            id = UUID.randomUUID(),
            description = "This is the long description that will be used 2",
            date = Date(2024, 11, 2),
            startTime = startTimeValue2,
            endTime = endTimeValue2,
            duration = FullHours(
                hours = endTimeValue2.hours - startTimeValue2.hours,
                minutes = endTimeValue2.minutes - startTimeValue2.minutes
            ),
            category = Category(UUID.randomUUID(), "Fun"),
            photograph = null
        )

        timeEntries += newTimeEntry2

        timeEntries += newTimeEntry
        timeEntries += newTimeEntry2

        timeEntries += newTimeEntry2
        timeEntries += newTimeEntry2
        timeEntries += newTimeEntry2
        timeEntries += newTimeEntry2


        timeEntries = timeEntries.sortedByDescending { it.date }
    }


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
                hours = endTimeValue.hours - startTimeValue.hours,
                minutes = endTimeValue.minutes - startTimeValue.minutes
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
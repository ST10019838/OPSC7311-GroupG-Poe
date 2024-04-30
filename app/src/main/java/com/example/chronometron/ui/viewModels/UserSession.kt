package com.example.chronometron.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.benlu.composeform.formatters.dateShort
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.forms.EntryCreationForm
import com.example.chronometron.types.Category
import com.example.chronometron.types.Period
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.utils.areShortDatesEqual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.UUID

object UserSession : ViewModel() {
    // The following search functionality was adapted from youtube
    // Author: Philipp Lackner
    // Link: https://www.youtube.com/watch?v=CfL6Dl2_dAE
    private val _selectedPeriod = MutableStateFlow(Period())
    private val selectedPeriod = _selectedPeriod.asStateFlow()

    private val _timeEntries = MutableStateFlow(listOf<TimeEntry>())
    val timeEntries = combine(_timeEntries) { entries ->
        entries[0].sortedByDescending { entry -> entry.date }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _timeEntries.value
    )


    private val _datesAndEntries = combine(timeEntries) { sortedEntries ->
        val datesAndEntriesMap = mutableMapOf<String, MutableList<Int>>()

        sortedEntries[0].forEachIndexed { index, entry ->
            val date = dateShort(entry.date)
            if (datesAndEntriesMap.containsKey(date)) {
                datesAndEntriesMap[date]?.add(index)
            } else {
                datesAndEntriesMap[dateShort(entry.date)] = mutableListOf(index)
            }
        }

        datesAndEntriesMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<String, MutableList<Int>>()
    )


    val datesAndEntries =
        selectedPeriod.combine(_datesAndEntries) { selectedPeriod, datesAndEntries ->
            if (selectedPeriod.fromDate == null && selectedPeriod.toDate == null) {
                datesAndEntries
            } else {
                val onlyFromDate = selectedPeriod.fromDate != null && selectedPeriod.toDate == null
                val onlyToDate = selectedPeriod.fromDate == null && selectedPeriod.toDate != null

                // To explain the complex looking if statement:
                // - IF "ToDate" is empty: show all dates before or equal to "ToDate"
                // - IF "FromDate" is empty: show all entries after or equal to "FromDate"
                // - Otherwise show all entries between (inclusive) the "FromDate" and "ToDate"
                datesAndEntries.filter { item ->
                    if (onlyToDate) {
                        Date(item.key).before(selectedPeriod.toDate) ||
                                areShortDatesEqual(
                                    shortDate = item.key,
                                    date = selectedPeriod.toDate!!
                                )
                    } else if (onlyFromDate) {
                        Date(item.key).after(selectedPeriod.fromDate) ||
                                areShortDatesEqual(
                                    shortDate = item.key,
                                    date = selectedPeriod.fromDate!!
                                )
                    } else Date(item.key).after(selectedPeriod.fromDate) &&
                            Date(item.key).before(selectedPeriod.toDate) ||
                            areShortDatesEqual(
                                shortDate = item.key,
                                date = selectedPeriod.fromDate!!
                            ) ||
                            areShortDatesEqual(
                                shortDate = item.key,
                                date = selectedPeriod.toDate!!
                            )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _datesAndEntries.value
        )

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()

    private val _minimumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val minimumGoal = _minimumGoal.asStateFlow()

    private val _maximumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val maximumGoal = _maximumGoal.asStateFlow()


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

        _timeEntries.update { it + newTimeEntry }

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

        _timeEntries.update { it + newTimeEntry2 }
        _timeEntries.update { it + newTimeEntry2 }
        _timeEntries.update { it + newTimeEntry2 }

    }


    fun onSelectedPeriodChange(fromDate: Date?, toDate: Date?) {
        _selectedPeriod.value = Period(fromDate, toDate)
    }


    // *Maybe convert to state
    fun getTotalDailyDuration(date: Date? = Date(), formattedDate: String? = null): Hours {
        var totalHours = 0
        var totalMinutes = 0

        val dateToUse = formattedDate ?: dateShort(date)

        timeEntries.value.forEach { entry ->
            if (dateToUse == dateShort(entry.date)) {
                totalHours += entry.duration.hours
                totalHours += entry.duration.minutes
            }
        }

        return FullHours(totalHours, totalMinutes)
    }


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

//        timeEntries += newTimeEntry

        _timeEntries.update { it + newTimeEntry }
    }


    fun updateMinimumGoal(goal: Hours) {
        _minimumGoal.update { goal }
    }

    fun updateMaximumGoal(goal: Hours) {
        _maximumGoal.update { goal }
    }


    fun addCategory(category: Category) {
        _categories.update { it + category }
    }

    fun removeCategory(category: Category) {
        _categories.update { it.minusElement(category) }
    }

}
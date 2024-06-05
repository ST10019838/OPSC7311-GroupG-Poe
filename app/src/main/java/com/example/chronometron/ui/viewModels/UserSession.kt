package com.example.chronometron.ui.viewModels

//import com.example.chronometron.db.LocalDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.benlu.composeform.formatters.dateShort
import co.yml.charts.common.model.Point
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.types.Period
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.utils.addFullHours
import com.example.chronometron.utils.areShortDatesEqual
import com.example.chronometron.utils.hoursToFloat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.UUID


object UserSession : ViewModel() {
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode = _isDarkMode.asStateFlow()

    // The following search functionality was adapted from youtube
    // Author: Philipp Lackner
    // Link: https://www.youtube.com/watch?v=CfL6Dl2_dAE
    private val _selectedPeriod = MutableStateFlow(Period())
    val selectedPeriod = _selectedPeriod.asStateFlow()

    private val _timeEntries = MutableStateFlow(listOf<TimeEntry>())

    val timeEntries = combine(_timeEntries) { entries ->
        entries[0].sortedByDescending { entry -> entry.date }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _timeEntries.value
    )


    private val _datesAndEntries = combine(timeEntries) { sortedEntries ->

        var datesAndEntriesMap = mutableMapOf<String, Pair<Hours, MutableList<Int>>>()

        sortedEntries[0].forEachIndexed { index, entry ->
            val date = dateShort(entry.date)
            if (datesAndEntriesMap.containsKey(date)) {
                var totalTime: Hours = FullHours(0, 0)
                datesAndEntriesMap[date]?.second?.add(index)

                datesAndEntriesMap[date]?.second?.forEach { id ->
                    totalTime = addFullHours(totalTime, _timeEntries.value[id].duration)
                }

                datesAndEntriesMap[date] =
                    Pair(
                        totalTime,
                        datesAndEntriesMap[date]?.second!!
                    )

            } else {
                datesAndEntriesMap[dateShort(entry.date)] =
                    Pair(entry.duration, mutableListOf(index))
            }
        }

        datesAndEntriesMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<String, Pair<Hours, MutableList<Int>>>()
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

//    private val _categories = db.categories.getCategories().stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        emptyList()
//    )
//
//    val categories = _categories

    val categoryHours = combine(datesAndEntries) {
        var categoryHoursMap = mutableMapOf<Category, Hours>()

        it[0].forEach { item ->
            item.value.second.forEach { id ->
                val entry = timeEntries.value[id]
                val category = entry.category

                if (categoryHoursMap.containsKey(category)) {
                    categoryHoursMap[category] =
                        addFullHours(categoryHoursMap[category]!!, entry.duration)

//                        FullHours(
//                        hours = categoryHoursMap[category]?.hours?.plus(entry.duration.hours) ?: 0,
//                        minutes = categoryHoursMap[category]?.minutes?.plus(entry.duration.minutes)
//                            ?: 0
//                    )
                } else {
                    categoryHoursMap[category] =
                        FullHours(entry.duration.hours, entry.duration.minutes)
                }
            }
        }

        categoryHoursMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<Category, Hours>()
    )


    private val _minimumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val minimumGoal = _minimumGoal.asStateFlow()

    private val _maximumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val maximumGoal = _maximumGoal.asStateFlow()

    val graphData = combine(datesAndEntries) {
        var pointsData = mutableListOf<Point>()

        it[0].toList().reversed().forEach { item ->
            // This if allows the graph data to be empty when there are no dates and entries,
            // allowing for error messages to be displayed
            if(pointsData.isEmpty()){
                pointsData += Point(0f, 0f, description = "")
            }

            pointsData += Point(
                pointsData.last().x  + 1f,
                hoursToFloat(item.second.first),
                description = item.first
            )
        }



        // list needs to be reversed to display dates in order
//        pointsData = pointsData.reversed().toMutableList()


//        if(pointsData.isNotEmpty()){
//            pointsData.add(0, Point(0f, 0f, description = ""))
//        }

        pointsData.toList()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf<Point>()
    )


//    val totalDailyDuration = combine(_datesAndEntries) {
//        var totalHours = 0
//        var totalMinutes = 0
//
//        it[0][dateShort(Date())]?.second?.forEach { id ->
//            totalHours += _timeEntries.value[id].duration.hours
//            totalHours += _timeEntries.value[id].duration.minutes
//        }
//
////        timeEntries.value.forEach { entry ->
////            if (dateToUse == dateShort(entry.date)) {
////
////            }
////        }
//
//        FullHours(totalHours, totalMinutes)
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        FullHours(0, 0)
//    )

    init {
        val newEntry = TimeEntry(
            id = UUID.randomUUID(),
            category = Category(id = UUID.randomUUID(), name = "CAT"),
            date = Date(),
            description = "",
            endTime = FullHours(0, 0),
            duration = FullHours(6, 20),
            startTime = FullHours(0, 0),
            photograph = null
        )

        val newEntry2 = TimeEntry(
            id = UUID.randomUUID(),
            category = Category(id = UUID.randomUUID(), name = "CAT"),
            date = Date(),
            description = "",
            endTime = FullHours(0, 0),
            duration = FullHours(2, 0),
            startTime = FullHours(0, 0),
            photograph = null
        )

        _timeEntries.update { (it + newEntry).toMutableList() }
//        _timeEntries.update { (it + newEntry).toMutableList() }
//        viewModelScope.launch {
////            //fetch data code
////            _timeEntries =
//            val other = LocalDatabase.getDatabase(getApplication<Application>().applicationContext)
//        }
//        db = LocalDatabase.getDatabase(getApplication())
    }

//    fun fetchData(){
//        screenModelScope.launch {
//            //fetch data code
//        }
//    }


    fun onSelectedPeriodChange(fromDate: Date?, toDate: Date?) {
        _selectedPeriod.value = Period(fromDate, toDate)
    }

//    fun getTotalDailyDuration(date: Date? = Date(), formattedDate: String? = null): Hours {
//        var totalHours = 0
//        var totalMinutes = 0
//
//        val dateToUse = formattedDate ?: dateShort(date)
//
//        datesAndEntries.value[dateToUse]?.forEach { id ->
//            totalHours += _timeEntries.value[id].duration.hours
//            totalHours += _timeEntries.value[id].duration.minutes
//        }
//
////        timeEntries.value.forEach { entry ->
////            if (dateToUse == dateShort(entry.date)) {
////                totalHours += entry.duration.hours
////                totalHours += entry.duration.minutes
////            }
////        }
//
//        return FullHours(totalHours, totalMinutes)
//    }


    fun addTimeEntry(newEntry: TimeEntry) {
        _timeEntries.update { (it + newEntry).toMutableList() }
    }

    fun updateTimeEntry(updatedEntry: TimeEntry) {
//        _timeEntries.update {
//            _timeEntries.value.map { entry ->
//                if (entry.id == updatedEntry.id) updatedEntry
//                else entry
//            }.toList()

//            var newList = _timeEntries.value.toMutableList()
//            val entryIndex = _timeEntries.value.indexOfFirst { entry -> entry.id == updatedEntry.id }
//            newList[entryIndex] = updatedEntry
//
//            newList
//        }

        _timeEntries.update {
            it.toMutableList().apply {
                this[indexOfFirst { entry -> entry.id == updatedEntry.id }] = updatedEntry
            }
        }
    }

    fun deleteTimeEntry(entry: TimeEntry) {
        _timeEntries.update { it.minusElement(entry) }
    }


    fun updateMinimumGoal(goal: Hours) {
        _minimumGoal.update { goal }
    }

    fun updateMaximumGoal(goal: Hours) {
        _maximumGoal.update { goal }
    }


    fun addCategory(category: Category) {
        _categories.update { it + category }
//        viewModelScope.launch {
        //fetch data code
//            db.categories.upsertCategory(category)
//            LocalDatabase.getDatabase(getApplication<Application>().applicationContext).categories.upsertCategory(
//                category
//            )
//        }
    }

    fun removeCategory(category: Category) {
        _categories.update { it.minusElement(category) }

//        viewModelScope.launch {
//            //fetch data code
//            db.categories.deleteCategory(category)
//        }
    }

    fun toggleIsDarkMode(value: Boolean){
        _isDarkMode.update { value }
    }

}

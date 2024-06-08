package com.example.chronometron.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.benlu.composeform.formatters.dateShort
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.*
import com.example.chronometron.utils.addFullHours
import com.example.chronometron.utils.areShortDatesEqual
import com.google.firebase.database.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

object UserSession : ViewModel() {
    private const val TAG = "UserSession"

    // Reference to Firebase Realtime Database
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Dark mode state
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode = _isDarkMode.asStateFlow()

    // Selected period state
    private val _selectedPeriod = MutableStateFlow(Period())
    val selectedPeriod = _selectedPeriod.asStateFlow()

    // Time entries state
    private val _timeEntries = MutableStateFlow(listOf<TimeEntry>())
    val timeEntries = combine(_timeEntries) { entries ->
        entries[0].filter { e -> !e.isArchived }.sortedByDescending { entry -> entry.date }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _timeEntries.value
    )

    // Dates and entries mapping
    private val _datesAndEntries = combine(timeEntries) { sortedEntries ->
        val datesAndEntriesMap = mutableMapOf<String, Pair<Hours, MutableList<Int>>>()
        sortedEntries[0].forEachIndexed { index, entry ->
            val date = dateShort(entry.date)
            if (datesAndEntriesMap.containsKey(date)) {
                var totalTime: Hours = FullHours(0, 0)
                datesAndEntriesMap[date]?.second?.add(index)
                datesAndEntriesMap[date]?.second?.forEach { id ->
                    totalTime = addFullHours(totalTime, _timeEntries.value[id].duration.toFullHours())
                }
                datesAndEntriesMap[date] = Pair(totalTime, datesAndEntriesMap[date]?.second!!)
            } else {
                datesAndEntriesMap[dateShort(entry.date)] = Pair(entry.duration.toFullHours(), mutableListOf(index))
            }
        }
        datesAndEntriesMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<String, Pair<Hours, MutableList<Int>>>()
    )

    // Filtered dates and entries based on selected period
    val datesAndEntries = selectedPeriod.combine(_datesAndEntries) { selectedPeriod, datesAndEntries ->
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
                            areShortDatesEqual(shortDate = item.key, date = selectedPeriod.toDate!!)
                } else if (onlyFromDate) {
                    Date(item.key).after(selectedPeriod.fromDate) ||
                            areShortDatesEqual(shortDate = item.key, date = selectedPeriod.fromDate!!)
                } else Date(item.key).after(selectedPeriod.fromDate) &&
                        Date(item.key).before(selectedPeriod.toDate) ||
                        areShortDatesEqual(shortDate = item.key, date = selectedPeriod.fromDate!!) ||
                        areShortDatesEqual(shortDate = item.key, date = selectedPeriod.toDate!!)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _datesAndEntries.value
    )

    // Archived time entries state
    val archivedTimeEntries = combine(_timeEntries) { entries ->
        entries[0].filter { e -> e.isArchived }.sortedByDescending { entry -> entry.date }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _timeEntries.value
    )

    // Archived dates and entries mapping
    private val _archivedDatesAndEntries = combine(archivedTimeEntries) { sortedEntries ->
        val datesAndEntriesMap = mutableMapOf<String, Pair<Hours, MutableList<Int>>>()
        sortedEntries[0].forEachIndexed { index, entry ->
            val date = dateShort(entry.date)
            if (datesAndEntriesMap.containsKey(date)) {
                var totalTime: Hours = FullHours(0, 0)
                datesAndEntriesMap[date]?.second?.add(index)
                datesAndEntriesMap[date]?.second?.forEach { id ->
                    totalTime = addFullHours(totalTime, _timeEntries.value[id].duration.toFullHours())
                }
                datesAndEntriesMap[date] = Pair(totalTime, datesAndEntriesMap[date]?.second!!)
            } else {
                datesAndEntriesMap[dateShort(entry.date)] = Pair(entry.duration.toFullHours(), mutableListOf(index))
            }
        }
        datesAndEntriesMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<String, Pair<Hours, MutableList<Int>>>()
    )

    // Filtered archived dates and entries based on selected period
    val archivedDatesAndEntries = selectedPeriod.combine(_archivedDatesAndEntries) { selectedPeriod, datesAndEntries ->
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
                            areShortDatesEqual(shortDate = item.key, date = selectedPeriod.toDate!!)
                } else if (onlyFromDate) {
                    Date(item.key).after(selectedPeriod.fromDate) ||
                            areShortDatesEqual(shortDate = item.key, date = selectedPeriod.fromDate!!)
                } else Date(item.key).after(selectedPeriod.fromDate) &&
                        Date(item.key).before(selectedPeriod.toDate) ||
                        areShortDatesEqual(shortDate = item.key, date = selectedPeriod.fromDate!!) ||
                        areShortDatesEqual(shortDate = item.key, date = selectedPeriod.toDate!!)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _archivedDatesAndEntries.value
    )

    // Categories state
    private val _categories = MutableStateFlow(listOf<Category>())
    val categories = _categories.asStateFlow()

    // Category hours calculation
    val categoryHours = combine(datesAndEntries) {
        val categoryHoursMap = mutableMapOf<Category, Hours>()
        it[0].forEach { item ->
            item.value.second.forEach { id ->
                val entry = timeEntries.value[id]
                val category = entry.category
                if (categoryHoursMap.containsKey(category)) {
                    categoryHoursMap[category] = addFullHours(categoryHoursMap[category]!!, entry.duration.toFullHours())
                } else {
                    categoryHoursMap[category] = FullHours(entry.duration.hours, entry.duration.minutes)
                }
            }
        }
        categoryHoursMap.toMap()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        mapOf<Category, Hours>()
    )

    // Minimum and maximum goal state
    private val _minimumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val minimumGoal = _minimumGoal.asStateFlow()

    private val _maximumGoal = MutableStateFlow<Hours>(FullHours(0, 0))
    val maximumGoal = _maximumGoal.asStateFlow()

    // Goals state
    private val _goals = MutableStateFlow<Goals?>(null)
    val goals = _goals.asStateFlow()

    init {
        // Fetch initial data from Firebase Realtime Database
        fetchCategories()
        fetchTimeEntries()
        fetchGoals()

        // Example of adding an entry for testing purposes
        val newEntry = TimeEntry(
            id = UUID.randomUUID().toString(),
            category = Category(id = UUID.randomUUID().toString(), name = "CAT"),
            date = Date(),
            description = "",
            endTime = SerializableHours(0, 0),
            duration = SerializableHours(6, 20),
            startTime = SerializableHours(0, 0),
            photograph = null,
            isArchived = true
        )

        _timeEntries.update { (it + newEntry).toMutableList() }
    }

    // Fetch categories from Firebase Realtime Database
    private fun fetchCategories() {
        db.child("categories").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoriesList = mutableListOf<Category>()
                if (!snapshot.exists()) {
                    // If no categories exist, create a default category
                    val defaultCategory = Category(
                        id = UUID.randomUUID().toString(),
                        name = "Default"
                    )
                    addCategory(defaultCategory)
                    categoriesList.add(defaultCategory)
                } else {
                    snapshot.children.forEach { child ->
                        val id = child.child("id").getValue(String::class.java) ?: UUID.randomUUID().toString()
                        val name = child.child("name").getValue(String::class.java) ?: "Unnamed Category"
                        Log.d(TAG, "Category ID: $id, Name: $name")
                        val category = Category(id = id, name = name)
                        categoriesList.add(category)
                    }
                }
                _categories.value = categoriesList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching categories", error.toException())
            }
        })
    }

    // Fetch time entries from Firebase Realtime Database
    private fun fetchTimeEntries() {
        db.child("timeEntries").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val timeEntriesList = snapshot.children.map { it.getValue(TimeEntry::class.java)!! }
                _timeEntries.value = timeEntriesList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching TimeEntries", error.toException())
            }
        })
    }

    // Fetch goals from Firebase Realtime Database
    private fun fetchGoals() {
        db.child("goals").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val goals = snapshot.getValue(Goals::class.java)
                _goals.value = goals
                goals?.let {
                    _minimumGoal.value = it.minimumHours.toFullHours()
                    _maximumGoal.value = it.maximumHours.toFullHours()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching goals", error.toException())
            }
        })
    }

    // Update selected period
    fun onSelectedPeriodChange(fromDate: Date?, toDate: Date?) {
        _selectedPeriod.value = Period(fromDate, toDate)
    }

    // Add new time entry to Firebase Realtime Database
    fun addTimeEntry(newEntry: TimeEntry) {
        _timeEntries.update { (it + newEntry).toMutableList() }
        db.child("timeEntries").child(newEntry.id).setValue(newEntry)
            .addOnSuccessListener {
                Log.d(TAG, "Time entry added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error adding Time Entry", exception)
            }
    }

    // Update existing time entry in Firebase Realtime Database
    fun updateTimeEntry(updatedEntry: TimeEntry) {
        _timeEntries.update {
            it.toMutableList().apply {
                val index = indexOfFirst { entry -> entry.id == updatedEntry.id }
                if (index != -1) {
                    this[index] = updatedEntry
                }
            }
        }
        db.child("timeEntries").child(updatedEntry.id).setValue(updatedEntry)
            .addOnSuccessListener {
                Log.d(TAG, "Time entry updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error updating Time Entry", exception)
            }
    }

    // Delete time entry from Firebase Realtime Database
    fun deleteTimeEntry(entry: TimeEntry) {
        _timeEntries.update { it.minusElement(entry) }
        db.child("timeEntries").child(entry.id).removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "Time entry deleted successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error deleting Time Entry", exception)
            }
    }

    // Update minimum goal
    fun updateMinimumGoal(goal: Hours) {
        _minimumGoal.update { goal }
        db.child("goals/minimumHours").setValue(SerializableHours.fromFullHours(goal))
            .addOnSuccessListener {
                Log.d(TAG, "Minimum goal updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error updating minimum goal", exception)
            }
    }

    // Update maximum goal
    fun updateMaximumGoal(goal: Hours) {
        _maximumGoal.update { goal }
        db.child("goals/maximumHours").setValue(SerializableHours.fromFullHours(goal))
            .addOnSuccessListener {
                Log.d(TAG, "Maximum goal updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error updating maximum goal", exception)
            }
    }

    // Add new category to Firebase Realtime Database
    fun addCategory(category: Category) {
        Log.d(TAG, "Category object before adding: $category")
        db.child("categories").push().setValue(category)
            .addOnSuccessListener {
                Log.d(TAG, "Category added successfully")
                _categories.update { it + category }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error adding category", exception)
            }
    }

    // Remove category from Firebase Realtime Database
    fun removeCategory(category: Category) {
        Log.d(TAG, "Deleting category with ID: ${category.id}")

        // Update local state to remove the category
        _categories.update { it.minusElement(category) }

        db.child("categories").child(category.id).removeValue()
            .addOnSuccessListener {
                Log.d(TAG, "Successfully removed category from database: ${category.id}")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to remove category from database with ID: ${category.id}", exception)
            }
    }

    // Toggle dark mode
    fun toggleIsDarkMode(value: Boolean) {
        _isDarkMode.update { value }
    }

    // Archive a time entry
    fun archiveEntry(entry: TimeEntry) {
        val updatedEntry = TimeEntry(
            id = entry.id,
            description = entry.description,
            date = entry.date,
            startTime = entry.startTime,
            endTime = entry.endTime,
            duration = entry.duration,
            category = entry.category,
            photograph = entry.photograph,
            isArchived = true
        )
        deleteTimeEntry(entry)
        addTimeEntry(updatedEntry)
    }

    // Unarchive a time entry
    fun unarchiveEntry(entry: TimeEntry) {
        val updatedEntry = TimeEntry(
            id = entry.id,
            description = entry.description,
            date = entry.date,
            startTime = entry.startTime,
            endTime = entry.endTime,
            duration = entry.duration,
            category = entry.category,
            photograph = entry.photograph,
            isArchived = false
        )
        deleteTimeEntry(entry)
        addTimeEntry(updatedEntry)
    }
}

//package com.example.chronometron.db.daos
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Query
//import androidx.room.Upsert
//import com.example.chronometron.types.TimeEntry
////import com.example.chronometron.types.TimeEntry
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface TimeEntryDao {
//    @Upsert
//    suspend fun upsertTimeEntry(entry: TimeEntry)
//
//    @Delete
//    suspend fun deleteTimeEntry(entry: TimeEntry)
//
//    @Query("SELECT * FROM timeentry ORDER BY date DESC")
//    fun getTimeEntries(): Flow<List<TimeEntry>>
//}
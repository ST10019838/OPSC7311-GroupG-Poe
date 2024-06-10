package com.example.chronometron.api

import com.example.chronometron.Firebase.Database.archivedEntries
import com.example.chronometron.Firebase.Database.unarchivedEntries
import com.example.chronometron.types.TimeEntry


fun addTimeEntry(newEntry: TimeEntry) {
    val newId = unarchivedEntries.push().key
    newEntry.id = newId!!
    unarchivedEntries.child(newId).setValue(newEntry)
}

fun updateTimeEntry(updatedEntry: TimeEntry) {
    if (updatedEntry.isArchived) {
        archivedEntries.child(updatedEntry.id).setValue(updatedEntry)
    } else {
        unarchivedEntries.child(updatedEntry.id).setValue(updatedEntry)
    }
}

fun deleteTimeEntry(entry: TimeEntry) {
    if (entry.isArchived) {
        archivedEntries.child(entry.id).setValue(null)
    } else {
        unarchivedEntries.child(entry.id).setValue(null)
    }
}


fun archiveEntry(entry: TimeEntry) {
    if(entry.isArchived) return

    deleteTimeEntry(entry)
    entry.isArchived = true
    archivedEntries.child(entry.id).setValue(entry)
}

fun unarchiveEntry(entry: TimeEntry) {
    if(!entry.isArchived) return

    deleteTimeEntry(entry)
    entry.isArchived = false
    unarchivedEntries.child(entry.id).setValue(entry)
}
package com.example.chronometron.db

import android.util.Log
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.viewModels.SessionState
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

object Database {
    // TODO don't forget to use child path after user auth
    val ref: DatabaseReference =
        Firebase.database("https://chronometron-test-default-rtdb.europe-west1.firebasedatabase.app/").reference

    private val entries = ref.child("entries")
    val unarchivedEntries = entries.child("unarchived")
    val archivedEntries = entries.child("archived")

    val isDarkMode = ref.child("isDarkMode")

    private val goals = ref.child("goals")
    val minimumGoal = goals.child("minimumGoal")
    val maximumGoal = goals.child("maximumGoal")

    val categories = ref.child("categories")


    init {
        val archivedEntriesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<Map<String, TimeEntry>>()
                SessionState.setArchivedTimeEntries(data?.values?.toList() ?: emptyList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }

        val unarchivedEntriesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<Map<String, TimeEntry>>()
                SessionState.setTimeEntries(data?.values?.toList() ?: emptyList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }


        val isDarkModeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<Boolean>()
                SessionState.setIsDarkMode(data ?: true)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }


        val minimumGoalListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<FirebaseHours>()
                SessionState.setMinimumGoal(data!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }

        val maximumGoalListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<FirebaseHours>()
                SessionState.setMaximumGoal(data!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }


        val categoriesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<Map<String, Category>>()
                SessionState.setCategories(data?.values?.toList() ?: emptyList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("YA", "loadPost:onCancelled", databaseError.toException())
            }
        }



        archivedEntries.removeEventListener(archivedEntriesListener)
        unarchivedEntries.addValueEventListener(unarchivedEntriesListener)
        isDarkMode.addValueEventListener(isDarkModeListener)
        minimumGoal.addValueEventListener(minimumGoalListener)
        maximumGoal.addValueEventListener(maximumGoalListener)
        categories.addValueEventListener(categoriesListener)


        archivedEntries.addValueEventListener(archivedEntriesListener)
        unarchivedEntries.addValueEventListener(unarchivedEntriesListener)
        isDarkMode.addValueEventListener(isDarkModeListener)
        minimumGoal.addValueEventListener(minimumGoalListener)
        maximumGoal.addValueEventListener(maximumGoalListener)
        categories.addValueEventListener(categoriesListener)


    }

}
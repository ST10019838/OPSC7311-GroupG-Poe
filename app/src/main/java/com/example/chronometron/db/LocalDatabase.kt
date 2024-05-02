//package com.example.chronometron.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.example.chronometron.db.daos.CategoryDao
//import com.example.chronometron.db.daos.TimeEntryDao
//import com.example.chronometron.types.Category
//import com.example.chronometron.types.TimeEntry
//
//@Database(
//    entities = [Category::class, TimeEntry::class],
//    version = 1,
//    exportSchema = false
//)
//@TypeConverters(Converters::class)
//abstract class LocalDatabase : RoomDatabase() {
//    abstract val categories: CategoryDao
//    abstract val timeEntries: TimeEntryDao
//
////    companion object {
////        @Volatile
////        private var Instance: LocalDatabase? = null
////
////        fun getDatabase(context: Context): LocalDatabase {
////            // if the Instance is not null, return it, otherwise create a new database instance.
////            return Instance ?: synchronized(this) {
////                Room.databaseBuilder(context, LocalDatabase::class.java, "item_database")
////                    .build()
////                    .also { Instance = it }
////            }
//
////            return Instance ?: synchronized(this) {
////                val instance = Room.databaseBuilder(
////                    context.applicationContext,
////                    LocalDatabase::class.java,
////                    "app_database"
////                ).build()
////
////                Instance = instance
////                instance
////            }
////        }
////    }
//}
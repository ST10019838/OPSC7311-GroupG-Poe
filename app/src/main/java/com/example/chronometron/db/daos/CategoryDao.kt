//package com.example.chronometron.db.daos;
//
//import androidx.room.Dao;
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Upsert
//import com.example.chronometron.types.Category
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface CategoryDao {
//    @Upsert
//    suspend fun upsertCategory(category: Category)
//
//    @Delete
//    suspend fun deleteCategory(category: Category)
//
//    @Query("SELECT * FROM category")
//    fun getCategories(): Flow<List<Category>>
//}

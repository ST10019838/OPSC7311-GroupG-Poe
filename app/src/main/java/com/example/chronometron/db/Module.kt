//package com.example.chronometron.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import com.example.chronometron.db.daos.CategoryDao
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//    // For getting the application context we have to add Application class
//    // and name of the class in manifest to get the context.
//    @Singleton
//    @Provides
//    fun provideCategoryDatabase(@ApplicationContext context: Context): LocalDatabase {
//        return Room.databaseBuilder(context, LocalDatabase::class.java, "local.db").build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserDao(db: LocalDatabase): CategoryDao {
//        return db.categories
//    }
//
//}
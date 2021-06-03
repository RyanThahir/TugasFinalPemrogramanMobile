package com.ryanthahir.astroapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Saved::class], exportSchema = false, version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun savedDao(): SavedDao
    companion object {
        private const val DB_NAME = "SAVED_DB"
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase? {
            if (instance == null) {
                synchronized(AppDataBase::class) {
                    instance = Room
                            .databaseBuilder(
                                    context,
                                    AppDataBase::class.java,
                                    DB_NAME
                            )
                            .build()
                }
            }
            return instance
        }
    }
}
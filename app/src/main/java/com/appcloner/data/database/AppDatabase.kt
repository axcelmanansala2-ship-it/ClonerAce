package com.appcloner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appcloner.data.model.ClonedApp

@Database(entities = [ClonedApp::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clonedAppDao(): ClonedAppDao
}

package com.appcloner.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.appcloner.data.database.AppDatabase
import com.appcloner.data.database.ClonedAppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "cloner_ace_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideClonedAppDao(db: AppDatabase): ClonedAppDao = db.clonedAppDao()

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}

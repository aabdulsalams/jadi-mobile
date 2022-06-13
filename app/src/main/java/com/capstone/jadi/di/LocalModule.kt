package com.capstone.jadi.di

import android.content.Context
import androidx.room.Room
import com.capstone.jadi.data.local.HistoryDatabase
import com.capstone.jadi.data.local.dao.HistoryDao
import com.capstone.jadi.utils.ConstVal.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.databaseBuilder(context, HistoryDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHistoryDao(database: HistoryDatabase): HistoryDao = database.getHistoryDao()

}
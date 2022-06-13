package com.capstone.jadi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.jadi.data.local.dao.HistoryDao
import com.capstone.jadi.data.local.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}
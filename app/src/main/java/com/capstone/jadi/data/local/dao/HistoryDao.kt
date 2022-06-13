package com.capstone.jadi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.jadi.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM tbl_history")
    fun getAllHistories(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistories(historyList: List<HistoryEntity>)

    @Query("DELETE FROM tbl_history")
    suspend fun deleteAllHistories()
}
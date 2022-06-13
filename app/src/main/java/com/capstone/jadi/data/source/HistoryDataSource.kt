package com.capstone.jadi.data.source

import com.capstone.jadi.data.local.dao.HistoryDao
import com.capstone.jadi.data.mapper.historyToHistoryEntity
import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.data.remote.history.GetHistoriesResponse
import com.capstone.jadi.data.remote.history.HistoryService
import com.capstone.jadi.data.remote.history.AddHistoriesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryDataSource @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyService: HistoryService
){
    suspend fun getAllHistories(token: String): Flow<ApiResponse<GetHistoriesResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = historyService.getAllHistories(token)
                if (!response.error) {
                    historyDao.deleteAllHistories()
                    val historyList = response.listHistory.map {
                        historyToHistoryEntity(it)
                    }
                    historyDao.insertHistories(historyList)
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun addNewHistory(token: String, name: RequestBody, file: MultipartBody.Part): Flow<ApiResponse<AddHistoriesResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = historyService.addNewHistory(token, name, file)
                if (!response.error) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

}
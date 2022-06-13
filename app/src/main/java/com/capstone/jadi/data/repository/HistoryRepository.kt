package com.capstone.jadi.data.repository

import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.data.remote.history.GetHistoriesResponse
import com.capstone.jadi.data.remote.history.AddHistoriesResponse
import com.capstone.jadi.data.source.HistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(private val historyDataSource: HistoryDataSource) {

    suspend fun getAllHistories(token: String): Flow<ApiResponse<GetHistoriesResponse>> {
        return historyDataSource.getAllHistories(token).flowOn(Dispatchers.IO)
    }

    suspend fun addNewHistory(token: String, name: RequestBody, file: MultipartBody.Part): Flow<ApiResponse<AddHistoriesResponse>> {
        return historyDataSource.addNewHistory(token, name, file )
    }

}
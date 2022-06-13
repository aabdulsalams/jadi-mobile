package com.capstone.jadi.data.remote.history

import com.capstone.jadi.data.remote.disease.DiseaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface HistoryService {

    @GET("histories")
    suspend fun getAllHistories(
        @Header("Authorization") token: String
    ): GetHistoriesResponse

    @Multipart
    @POST("histories")
    suspend fun addNewHistory(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part file: MultipartBody.Part,
    ): AddHistoriesResponse
}
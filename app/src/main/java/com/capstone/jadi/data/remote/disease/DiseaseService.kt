package com.capstone.jadi.data.remote.disease

import com.capstone.jadi.data.model.DetailDisease
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DiseaseService {
    @GET("users/{{diseaseId}}")
    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXItMk1xbUp6bUpkM2JYSkl4YSIsImlhdCI6MTY1NDk2Mjg4N30.zSVgge5NZFunM-26S1nhe_1HFOjmrhgO3fMilEPMMBU")
    fun getDiseaseDetail(
        @Path("diseaseId") diseaseId: String
    ): Call<DetailDisease>
}
package com.capstone.jadi.data.remote.disease

import com.capstone.jadi.data.model.DetailDisease
import com.google.gson.annotations.SerializedName

data class DiseaseResponse(
    @SerializedName("status_response")
    val message: String,
    @SerializedName("disease")
    val listDisease: DetailDisease,
    @SerializedName("errors")
    val error: Boolean
)

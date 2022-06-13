package com.capstone.jadi.data.model

import com.google.gson.annotations.SerializedName

data class DetailDisease(
    @SerializedName("status_response")
    val status: String,
    @SerializedName("id")
    val diseaseId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("control")
    val control: String,
    @SerializedName("errors")
    val error: String
)

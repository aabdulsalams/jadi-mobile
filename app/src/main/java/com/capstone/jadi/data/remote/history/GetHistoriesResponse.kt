package com.capstone.jadi.data.remote.history

import com.capstone.jadi.data.model.History
import com.google.gson.annotations.SerializedName

data class GetHistoriesResponse(
    @SerializedName("status_response")
    val message: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("histories")
    val listHistory: List<History>,
    @SerializedName("errors")
    val error: Boolean
)

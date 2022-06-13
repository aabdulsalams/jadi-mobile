package com.capstone.jadi.data.remote.history

import com.capstone.jadi.data.model.History
import com.google.gson.annotations.SerializedName

data class AddHistoriesResponse(

    @SerializedName("status_response")
    val message: String,
    @SerializedName("history")
    val listHistory: History,
    @SerializedName("errors")
    val error: Boolean

)

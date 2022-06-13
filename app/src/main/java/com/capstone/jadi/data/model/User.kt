package com.capstone.jadi.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val userId: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("accessToken")
    val token: String
)

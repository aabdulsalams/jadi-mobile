package com.capstone.jadi.data.remote.auth

import com.capstone.jadi.data.model.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("auth")
    val auth: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errors")
    val error: Boolean,
    @SerializedName("userResult")
    val loginResult: User,
)
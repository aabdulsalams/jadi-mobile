package com.capstone.jadi.data.remote.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/signup")
    suspend fun registerUser(
        @Body authBody: AuthBody
    ): Response<AuthResponse>

    @POST("auth/signin")
    suspend fun loginUser(
        @Body loginBody: LoginBody
    ): AuthResponse

}
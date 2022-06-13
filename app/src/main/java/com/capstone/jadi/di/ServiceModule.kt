package com.capstone.jadi.di

import com.capstone.jadi.data.remote.auth.AuthService
import com.capstone.jadi.data.remote.history.HistoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    fun provideHistoryService(retrofit: Retrofit): HistoryService = retrofit.create(HistoryService::class.java)


}
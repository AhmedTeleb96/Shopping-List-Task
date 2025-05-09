package com.example.shopping_list_module.di

import com.example.shopping_list_module.data.dataSources.remote.FakeRemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule
{
    @Provides
    @Singleton
    fun provideFakeRemoteApi(): FakeRemoteApi = FakeRemoteApi()
}
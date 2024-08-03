package com.example.projectandroid2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi
            = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit
            = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

}

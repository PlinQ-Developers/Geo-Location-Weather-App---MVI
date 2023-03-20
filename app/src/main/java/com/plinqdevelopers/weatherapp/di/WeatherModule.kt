package com.plinqdevelopers.weatherapp.di

import com.plinqdevelopers.weatherapp.data.repository.PlacesRepoImpl
import com.plinqdevelopers.weatherapp.data.repository.WeatherRepoImpl
import com.plinqdevelopers.weatherapp.data.source.remote.WeatherApi
import com.plinqdevelopers.weatherapp.domain.repository.PlacesRepo
import com.plinqdevelopers.weatherapp.domain.repository.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.WEATHER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().also {
                            it.level = HttpLoggingInterceptor.Level.BODY
                        },
                    )
                    .build(),
            )
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepo(
        api: WeatherApi,
    ): WeatherRepo {
        return WeatherRepoImpl(
            api = api,
        )
    }

    @Provides
    @Singleton
    fun providePlacesRepo(
        api: WeatherApi,
    ): PlacesRepo {
        return PlacesRepoImpl(
            api = api,
        )
    }
}

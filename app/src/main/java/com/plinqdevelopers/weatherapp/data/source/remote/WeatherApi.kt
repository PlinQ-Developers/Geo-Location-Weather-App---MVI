package com.plinqdevelopers.weatherapp.data.source.remote

import com.plinqdevelopers.weatherapp.data.source.remote.dto.places.PlaceDto
import com.plinqdevelopers.weatherapp.data.source.remote.dto.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("days") forecastDays: Int,
    ): WeatherDto

    @GET("/v1/search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): List<PlaceDto>

    companion object {
        const val WEATHER_API_BASE_URL = "https://api.weatherapi.com/v1/"
    }
}

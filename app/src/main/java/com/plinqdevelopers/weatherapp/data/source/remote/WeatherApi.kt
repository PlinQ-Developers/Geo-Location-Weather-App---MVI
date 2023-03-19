package com.plinqdevelopers.weatherapp.data.source.remote

import com.plinqdevelopers.weatherapp.data.source.remote.dto.weather.WeatherDto
import retrofit2.http.GET

interface WeatherApi {

    @GET("forecast.json?key=a9e8e8fd218f428ea52104029231903&q=Nairobi&days=3")
    suspend fun getWeatherForecast(): WeatherDto

    companion object {
        const val WEATHER_API_BASE_URL = "https://api.weatherapi.com/v1/"
    }
}

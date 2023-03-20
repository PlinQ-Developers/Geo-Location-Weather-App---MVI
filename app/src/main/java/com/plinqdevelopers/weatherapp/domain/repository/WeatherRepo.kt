package com.plinqdevelopers.weatherapp.domain.repository

import com.plinqdevelopers.weatherapp.data.source.remote.dto.weather.WeatherDto

interface WeatherRepo {
    suspend fun getWeatherForecast(
        key: String,
        city: String,
        days: Int,
    ): WeatherDto
}

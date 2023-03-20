package com.plinqdevelopers.weatherapp.data.repository

import com.plinqdevelopers.weatherapp.data.source.remote.WeatherApi
import com.plinqdevelopers.weatherapp.data.source.remote.dto.weather.WeatherDto
import com.plinqdevelopers.weatherapp.domain.repository.WeatherRepo
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val api: WeatherApi,
) : WeatherRepo {

    override suspend fun getWeatherForecast(
        key: String,
        city: String,
        days: Int,
    ): WeatherDto {
        return api.getWeatherForecast(
            apiKey = key,
            cityName = city,
            forecastDays = days,
        )
    }
}

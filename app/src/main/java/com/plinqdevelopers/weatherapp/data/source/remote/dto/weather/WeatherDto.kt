package com.plinqdevelopers.weatherapp.data.source.remote.dto.weather

import com.plinqdevelopers.weatherapp.data.source.remote.dto.forecast.ForecastDto
import com.plinqdevelopers.weatherapp.domain.model.Weather

data class WeatherDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto,
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        placeName = location.name,
    )
}

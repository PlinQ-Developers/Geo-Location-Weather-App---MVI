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
        windSpeed = "${current.windMph} mph",
        humidity = "${current.humidity}%",
        dayType = current.condition.text,
        dayTypeIcon = "https:${current.condition.icon}",
        dayTemperature = "${current.tempC}°C",

        forecastTodayTemp = "${forecast.forecastday[0].day.avgTempC}° / ${forecast.forecastday[0].day.avgTempF}°F",
        forecastTomorrowTemp = "${forecast.forecastday[1].day.avgTempC}° / ${forecast.forecastday[1].day.avgTempF}°F",
        forecastDay3Temp = "${forecast.forecastday[2].day.avgTempC}° / ${forecast.forecastday[2].day.avgTempF}°F",

        forecastTodayIcon = "https:${forecast.forecastday[0].day.condition.icon}",
        forecastTomorrowIcon = "https:${forecast.forecastday[1].day.condition.icon}",
        forecastDay3Icon = "https:${forecast.forecastday[2].day.condition.icon}",
    )
}

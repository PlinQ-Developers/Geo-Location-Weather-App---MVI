package com.plinqdevelopers.weatherapp.domain.model

data class Weather(
    val placeName: String,
    val windSpeed: String,
    val humidity: String,
    val dayType: String,
    val dayTypeIcon: String,
    val dayTemperature: String,

    val forecastTodayTemp: String,
    val forecastTodayIcon: String,
    val forecastTomorrowTemp: String,
    val forecastTomorrowIcon: String,
    val forecastDay3Temp: String,
    val forecastDay3Icon: String,
)

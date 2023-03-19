package com.plinqdevelopers.weatherapp.data.source.remote.dto.weather

data class CurrentConditionDto(
    val code: Int,
    val icon: String,
    val text: String,
)

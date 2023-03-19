package com.plinqdevelopers.weatherapp.data.source.remote.dto.forecast

import com.google.gson.annotations.SerializedName
import com.plinqdevelopers.weatherapp.data.source.remote.dto.forecast.hour.HourlyDto

data class ForecastDataDto(
    val astro: ForecastAstroDto,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: ForecastDayDto,
    val hour: List<HourlyDto>,
)

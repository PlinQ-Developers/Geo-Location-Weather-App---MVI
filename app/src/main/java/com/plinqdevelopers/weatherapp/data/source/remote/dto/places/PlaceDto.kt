package com.plinqdevelopers.weatherapp.data.source.remote.dto.places

import com.plinqdevelopers.weatherapp.domain.model.Place

data class PlaceDto(
    val id: String,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String,
)

fun PlaceDto.toPlace(): Place {
    return Place(
        name = name,
        country = country,
    )
}

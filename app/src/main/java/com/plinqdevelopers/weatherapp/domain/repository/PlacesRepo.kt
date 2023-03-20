package com.plinqdevelopers.weatherapp.domain.repository

import com.plinqdevelopers.weatherapp.data.source.remote.dto.places.PlaceDto

interface PlacesRepo {

    suspend fun getPlacesList(name: String): List<PlaceDto>
}

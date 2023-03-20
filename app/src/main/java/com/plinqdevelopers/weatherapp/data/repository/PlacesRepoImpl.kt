package com.plinqdevelopers.weatherapp.data.repository

import com.plinqdevelopers.weatherapp.core.Constants
import com.plinqdevelopers.weatherapp.data.source.remote.WeatherApi
import com.plinqdevelopers.weatherapp.data.source.remote.dto.places.PlaceDto
import com.plinqdevelopers.weatherapp.domain.repository.PlacesRepo
import javax.inject.Inject

class PlacesRepoImpl @Inject constructor(
    private val api: WeatherApi,
) : PlacesRepo {
    override suspend fun getPlacesList(name: String): List<PlaceDto> {
        return api.searchLocations(
            apiKey = Constants.API_KEY,
            query = name,
        )
    }
}

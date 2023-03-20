package com.plinqdevelopers.weatherapp.presentation.weather

import com.plinqdevelopers.weatherapp.domain.model.Place
import com.plinqdevelopers.weatherapp.domain.model.Weather

sealed class WeatherFragmentContract {
    // States: Represent the current state of the fragment
    data class State(
        val isLoading: Boolean = false,
        val data: Weather? = null,
        val errorMessage: String? = null,
        val isSearchViewVisible: Boolean = false,
        val selectedCity: String = "",
        val placesList: List<Place>? = null,
    )

    // Effects: represents a side effect the fragment can perform
    sealed class Effect {
        data class ShowSnackMessage(val message: String) : Effect()

        object ShowSearchView : Effect()
        object HideSearchView : Effect()
    }

    // Events: represents all actions a user can do within the screen
    sealed class Event {
        data class GetWeatherForecast(val locationName: String) : Event()
        data class SearchPlaceQuery(val searchText: String) : Event()
        object OpenSearchContainer : Event()
        object CloseSearchContainer : Event()
    }
}

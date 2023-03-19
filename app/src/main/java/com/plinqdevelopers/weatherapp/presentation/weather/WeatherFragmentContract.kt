package com.plinqdevelopers.weatherapp.presentation.weather

import com.plinqdevelopers.weatherapp.domain.model.Weather

sealed class WeatherFragmentContract {
    // States: Represent the current state of the fragment
    data class State(
        val isLoading: Boolean = false,
        val data: Weather? = null,
        val errorMessage: String? = null,
        val isSearchViewVisible: Boolean = false,
    )

    // Effects: represents a side effect the fragment can perform
    sealed class Effect {
        data class ShowToast(val message: String) : Effect()
        data class ShowSnackMessage(val message: String) : Effect()

        object ShowSearchView : Effect()
    }

    // Events: represents all actions a user can do within the screen
    sealed class Event {
        data class GetWeatherForecast(val locationName: String) : Event()
        object ShowSearchView : Event()
        object CloseSearchView : Event()
    }
}

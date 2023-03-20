package com.plinqdevelopers.weatherapp.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plinqdevelopers.weatherapp.core.Constants
import com.plinqdevelopers.weatherapp.core.Resource
import com.plinqdevelopers.weatherapp.domain.use_cases.GetPlacesUseCase
import com.plinqdevelopers.weatherapp.domain.use_cases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getPlacesUseCase: GetPlacesUseCase,
) : ViewModel() {

    private val _weatherFragmentState = MutableLiveData<WeatherFragmentContract.State>()
    val state: LiveData<WeatherFragmentContract.State> = _weatherFragmentState

    fun handleEvents(
        event: WeatherFragmentContract.Event,
    ) {
        when (event) {
            is WeatherFragmentContract.Event.GetWeatherForecast -> {
                getWeatherForecast(placeName = event.locationName)
            }
            is WeatherFragmentContract.Event.ShowSearchView -> {
                _weatherFragmentState.value = WeatherFragmentContract.State(isSearchViewVisible = true)
            }
            is WeatherFragmentContract.Event.CloseSearchView -> {
                _weatherFragmentState.value = WeatherFragmentContract.State(isSearchViewVisible = false)
            }
            is WeatherFragmentContract.Event.SearchPlaceQuery -> {
                searchPlaces(placeName = event.searchText)
            }
        }
    }

    init {
        viewModelScope.launch {
            getWeatherForecast(
                placeName = Constants.DEFAULT_CITY,
            )
            searchPlaces(placeName = "L")
        }
    }

    private fun getWeatherForecast(placeName: String) {
        getWeatherUseCase(placeName).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _weatherFragmentState.value = WeatherFragmentContract.State(isLoading = true)
                }
                is Resource.Success -> {
                    val weatherData = result.data
                    _weatherFragmentState.value = WeatherFragmentContract.State(data = weatherData)
                }
                is Resource.Error -> {
                    _weatherFragmentState.value = WeatherFragmentContract.State(
                        errorMessage = result.message ?: "An unexpected error occurred!",
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchPlaces(placeName: String) {
        getPlacesUseCase(placeName).onEach { placeResult ->
            when (placeResult) {
                is Resource.Loading -> {
                    _weatherFragmentState.value = WeatherFragmentContract.State(isLoading = true)
                }
                is Resource.Success -> {
                    val data = placeResult.data
                    _weatherFragmentState.value = WeatherFragmentContract.State(placesList = data)
                }
                is Resource.Error -> {
                    _weatherFragmentState.value = WeatherFragmentContract.State(
                        errorMessage = placeResult.message ?: "An unexpected error occurred!",
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

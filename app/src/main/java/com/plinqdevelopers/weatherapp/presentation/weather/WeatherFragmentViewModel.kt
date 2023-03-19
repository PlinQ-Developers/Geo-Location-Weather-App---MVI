package com.plinqdevelopers.weatherapp.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plinqdevelopers.weatherapp.core.Resource
import com.plinqdevelopers.weatherapp.domain.use_cases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _weatherFragmentState = MutableLiveData<WeatherFragmentContract.State>()
    val state: LiveData<WeatherFragmentContract.State> = _weatherFragmentState

    fun handleEvents(
        event: WeatherFragmentContract.Event,
    ) {
        when (event) {
            is WeatherFragmentContract.Event.GetWeatherForecast -> {
                getWeatherForecast()
            }
            is WeatherFragmentContract.Event.ShowSearchView -> {
                _weatherFragmentState.value = WeatherFragmentContract.State(isSearchViewVisible = true)
            }
            is WeatherFragmentContract.Event.CloseSearchView -> {
                _weatherFragmentState.value = WeatherFragmentContract.State(isSearchViewVisible = false)
            }
        }
    }

    private fun getWeatherForecast() {
        getWeatherUseCase().onEach { result ->
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
}

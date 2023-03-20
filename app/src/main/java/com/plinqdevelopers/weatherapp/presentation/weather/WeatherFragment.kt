package com.plinqdevelopers.weatherapp.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.plinqdevelopers.weatherapp.databinding.FragmentWeatherBinding
import com.plinqdevelopers.weatherapp.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherFragmentViewModel by viewModels()
    private val localDateTime = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        observeState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.weatherFragmentToolbarContainer.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.GetWeatherForecast(
                    locationName = "",
                ),
            )
        }

        binding.weatherFragmentIvWeatherIcon.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.ShowSearchView,
            )
        }

        binding.weatherFragmentIvCloseSearch.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.CloseSearchView,
            )
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> showLoading()
                state.errorMessage != null -> showError(state.errorMessage)
                state.isSearchViewVisible -> showSearchView(state.isSearchViewVisible)
                state.data != null -> showWeatherData(state.data)
            }
        }
    }

    private fun showLoading() {
        binding.weatherFragmentPbLoadingData.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE
    }

    private fun showSearchView(isSearchVisible: Boolean) {
        if (isSearchVisible) {
            binding.weatherFragmentSearchViewContainer.visibility = View.VISIBLE
        } else {
            binding.weatherFragmentSearchViewContainer.visibility = View.GONE
        }
    }

    private fun showWeatherData(data: Weather) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE

        binding.apply {
            weatherFragmentTvTime.text = getTime()
            weatherFragmentTvDateToday.text = getDate()
            weatherFragmentTvPlaceName.text = data.placeName
            weatherFragmentIvWeatherIcon.load(data.dayTypeIcon)
            weatherFragmentTvTempValue.text = data.dayTemperature
            weatherFragmentTvWindValue.text = data.windSpeed
            weatherFragmentTvHumidityValue.text = data.humidity
            weatherFragmentTvWeatherDescription.text = data.dayType

            weatherFragmentTvForecastTodayValue.text = data.forecastTodayTemp
            weatherFragmentIvForecastToday.load(data.forecastTodayIcon)

            weatherFragmentTvForecastTomorrowValue.text = data.forecastTomorrowTemp
            weatherFragmentIvForecastTomorrow.load(data.forecastTomorrowIcon)

            weatherFragmentTvForecastThirdValue.text = data.forecastDay3Temp
            weatherFragmentIvForecastThird.load(data.forecastDay3Icon)
        }
    }

    private fun getTime(): String {
        return "${localDateTime.toLocalTime().hour}:${localDateTime.toLocalTime().minute} ${localDateTime.toLocalTime().format(
            DateTimeFormatter.ofPattern("a"),
        )}"
    }

    private fun getDate(): String {
        val dayOfWeek = localDateTime.dayOfWeek.toString().lowercase(Locale.ROOT)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val dayOfMonth = localDateTime.dayOfMonth
        val month = localDateTime.month.toString().lowercase(Locale.ROOT)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val year = localDateTime.year
        return "$dayOfWeek, $dayOfMonth $month $year"
    }
}

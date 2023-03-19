package com.plinqdevelopers.weatherapp.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.plinqdevelopers.weatherapp.databinding.FragmentWeatherBinding
import com.plinqdevelopers.weatherapp.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherFragmentViewModel by viewModels()

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
        binding.weatherFragmentTvPlaceName.text = data.placeName
    }
}

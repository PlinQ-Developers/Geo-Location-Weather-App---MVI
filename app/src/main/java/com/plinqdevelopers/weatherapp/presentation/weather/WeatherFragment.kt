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
        binding.textView.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.GetWeatherForecast(
                    locationName = "",
                ),
            )
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> showLoading()
                state.errorMessage != null -> showError(state.errorMessage)
                state.isSearchViewVisible -> showSearchView()
                state.data != null -> showWeatherData(state.data)
            }
        }
    }

    private fun showLoading() {
        binding.weatherFragmentPbLoadingData.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE
        binding.textView.text = message
    }

    private fun showSearchView() {
    }

    private fun showWeatherData(data: Weather) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE
        binding.textView.text = data.placeName
    }
}

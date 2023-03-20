package com.plinqdevelopers.weatherapp.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.plinqdevelopers.weatherapp.R
import com.plinqdevelopers.weatherapp.core.getCurrentFormattedDate
import com.plinqdevelopers.weatherapp.core.getCurrentFormattedTime
import com.plinqdevelopers.weatherapp.databinding.FragmentWeatherBinding
import com.plinqdevelopers.weatherapp.domain.model.Place
import com.plinqdevelopers.weatherapp.domain.model.Weather
import com.plinqdevelopers.weatherapp.presentation.adapters.PlaceListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(), PlaceListAdapter.PlaceItemClickListener {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherFragmentViewModel by viewModels()

    private val placeListAdapter: PlaceListAdapter = PlaceListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        observeState()
        observeEffect()
        setupSearchListView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        getSearchText()
    }

    private fun setupViews() {
        binding.weatherFragmentSearchButton.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.OpenSearchContainer,
            )
        }
        binding.weatherFragmentBtnCloseSearchWindow.setOnClickListener {
            viewModel.handleEvents(
                WeatherFragmentContract.Event.CloseSearchContainer,
            )
        }
    }

    private fun setupSearchListView() {
        binding.searchViewContainerSuggestionsList.apply {
            adapter = placeListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> showLoading()
                state.errorMessage != null -> showError(state.errorMessage)
                state.data != null -> showWeatherData(state.data)
                state.placesList != null -> populatePlacesList(state.placesList)
            }
        }
    }

    private fun observeEffect() {
        viewModel.effects.observe(viewLifecycleOwner) { effect ->
            when (effect) {
                is WeatherFragmentContract.Effect.ShowSearchView -> {
                    binding.weatherFragmentSearchViewContainer.visibility = View.VISIBLE
                }
                is WeatherFragmentContract.Effect.ShowSnackMessage -> {
                    val snack = Snackbar.make(
                        requireView(),
                        effect.message,
                        Snackbar.LENGTH_INDEFINITE,
                    )
                    snack.setAction("Ok") {
                        snack.dismiss()
                    }.show()
                }
                is WeatherFragmentContract.Effect.HideSearchView -> {
                    binding.weatherFragmentSearchViewContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun showLoading() {
        binding.weatherFragmentPbLoadingData.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE
        binding.weatherFragmentTvMessage.text = message
    }

    private fun populatePlacesList(placesList: List<Place>) {
        if (placesList.isEmpty()) {
            binding.weatherFragmentTvSearchInfo.visibility = View.VISIBLE
            binding.weatherFragmentTvSearchInfo.text = resources.getString(R.string.searchTextNotFound)
        } else {
            placeListAdapter.submitList(placesList)
            binding.weatherFragmentTvSearchInfo.visibility = View.GONE
        }
    }

    override fun onPlaceItemClicked(placeName: String) {
        viewModel.handleEvents(
            event = WeatherFragmentContract.Event.GetWeatherForecast(
                locationName = placeName,
            ),
        )
    }

    private fun getSearchText() {
        binding.weatherFragmentSvSearchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryCityOnInput(searchText = query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryCityOnInput(searchText = newText.toString())
                return true
            }
        })
    }

    private fun queryCityOnInput(searchText: String) {
        viewModel.handleEvents(
            event = WeatherFragmentContract.Event.SearchPlaceQuery(
                searchText = searchText,
            ),
        )
    }

    private fun showWeatherData(data: Weather) {
        binding.weatherFragmentPbLoadingData.visibility = View.GONE

        binding.apply {
            weatherFragmentTvTime.text = getCurrentFormattedTime()
            weatherFragmentTvDateToday.text = getCurrentFormattedDate()
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

            showIconsAndTextAfterLoadingComplete()
        }
    }

    private fun showIconsAndTextAfterLoadingComplete() {
        binding.apply {
            weatherFragmentTvMessage.visibility = View.GONE
            weatherFragmentSearchButton.visibility = View.VISIBLE
            weatherFragmentIvWindIcon.visibility = View.VISIBLE
            weatherFragmentIvHumidityIcon.visibility = View.VISIBLE

            weatherFragmentTvForecastTodayName.visibility = View.VISIBLE
            weatherFragmentTvForecastTomorrowName.visibility = View.VISIBLE
            weatherFragmentTvForecastThirdName.visibility = View.VISIBLE
        }
    }
}

package com.plinqdevelopers.weatherapp.domain.use_cases // ktlint-disable package-name

import com.plinqdevelopers.weatherapp.core.Constants
import com.plinqdevelopers.weatherapp.core.Resource
import com.plinqdevelopers.weatherapp.data.source.remote.dto.weather.toWeather
import com.plinqdevelopers.weatherapp.domain.model.Weather
import com.plinqdevelopers.weatherapp.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepo,
) {
    operator fun invoke(
        placeName: String,
    ): Flow<Resource<Weather>> = flow {
        try {
            emit(
                Resource.Loading(),
            )
            val weatherDto = repository.getWeatherForecast(
                key = Constants.API_KEY,
                city = placeName,
                days = Constants.DEFAULT_FORECAST_DAYS,
            )
            emit(
                Resource.Success(
                    data = weatherDto.toWeather(),
                ),
            )
        } catch (httpException: HttpException) {
            emit(
                Resource.Error(
                    message = httpException.localizedMessage ?: "An unexpected error occurred: Http!",
                ),
            )
        } catch (ioException: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection!",
                ),
            )
        }
    }
}

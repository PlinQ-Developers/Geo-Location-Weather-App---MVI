package com.plinqdevelopers.weatherapp.domain.use_cases // ktlint-disable package-name

import com.plinqdevelopers.weatherapp.core.Resource
import com.plinqdevelopers.weatherapp.data.source.remote.dto.places.toPlace
import com.plinqdevelopers.weatherapp.domain.model.Place
import com.plinqdevelopers.weatherapp.domain.repository.PlacesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val repository: PlacesRepo,
) {
    operator fun invoke(placeName: String): Flow<Resource<List<Place>>> = flow {
        try {
            emit(
                Resource.Loading(),
            )

            val placesDtoList = repository.getPlacesList(name = placeName)
            emit(
                Resource.Success(
                    data = placesDtoList.map { placeDto ->
                        placeDto.toPlace()
                    },
                ),
            )
        } catch (httpException: HttpException) {
            emit(
                Resource.Error(
                    message = httpException.localizedMessage ?: "An unexpected error occurred!",
                ),
            )
        } catch (ioException: IOException) {
            emit(
                Resource.Error(
                    message = "Please check your internet connection to continue!",
                ),
            )
        }
    }
}

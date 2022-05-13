package com.example.weather.data.data_providers

import com.example.weather.data.models.CurrentForecastModel
import com.example.weather.data.models.ForecastsModel
import retrofit2.http.GET

interface RemoteDataProvider {

    @GET("estonia/forecast")
    suspend fun getForecasts(): ForecastsModel

    @GET("estonia/current")
    suspend fun getCurrentForecast(): CurrentForecastModel

}
package com.example.weather.data.data_providers

import androidx.room.withTransaction
import com.example.weather.data.database.ForecastDatabase
import com.example.weather.helper.util.networkBoundResource
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteDataProvider: RemoteDataProvider,
    private val forecastDatabase: ForecastDatabase
){

    private val forecastDao = forecastDatabase.forecastDao()

    fun getForecast() = networkBoundResource(
        query = {
            forecastDao.getAllForecast()
        },
        fetch = {
            remoteDataProvider.getForecasts()
        },
        save = {
            forecastDatabase.withTransaction {
                forecastDao.deleteForecast()
                forecastDao.insertForecast(it)
            }
        }
    )

    fun getCurrentForecast() = networkBoundResource(
        query = {
            forecastDao.getAllCurrentForecast()
        },
        fetch = {
            remoteDataProvider.getCurrentForecast()
        },
        save = {
            forecastDatabase.withTransaction {
                forecastDao.deleteCurrentForecast()
                forecastDao.insertCurrentForecast(it)
            }
        }
    )

}
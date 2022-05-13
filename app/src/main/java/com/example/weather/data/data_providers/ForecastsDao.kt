package com.example.weather.data.data_providers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.models.CurrentForecastModel
import com.example.weather.data.models.ForecastsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastsDao {

    @Query("SELECT * FROM forecasts")
    fun getAllForecast(): Flow<ForecastsModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecastModel: ForecastsModel)

    @Query("DELETE FROM forecasts")
    suspend fun deleteForecast()

    @Query("SELECT * FROM current_forecast")
    fun getAllCurrentForecast(): Flow<CurrentForecastModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentForecast(currentForecastModel: CurrentForecastModel)

    @Query("DELETE FROM current_forecast")
    suspend fun deleteCurrentForecast()

}
package com.example.weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "current_forecast")
data class CurrentForecastModel(

    @PrimaryKey val timestamp: String,
    var observations: List<ObservationModel> = listOf()

)


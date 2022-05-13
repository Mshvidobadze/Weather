package com.example.weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastsModel(

    @PrimaryKey var forecasts: List<ForecastModel> = listOf()

)
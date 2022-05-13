package com.example.weather.data.models

data class ForecastModel(
    val date: String,
    val day: WeatherModel?,
    val night: WeatherModel?
)



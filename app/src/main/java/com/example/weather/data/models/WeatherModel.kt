package com.example.weather.data.models

data class WeatherModel(
    val phenomenon: String,
    val tempmin: Int,
    val tempmax: Int,
    val text: String,
    val sea: String,
    val peipsi: String,
    val places: List<PlaceModel>,
    val winds: List<WindModel>
)



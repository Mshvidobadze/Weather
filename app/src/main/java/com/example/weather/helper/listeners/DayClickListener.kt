package com.example.weather.helper.listeners

import com.example.weather.data.models.ForecastModel

interface DayClickListener {

    fun onDayClick(index: Int, forecastModel: ForecastModel)

}
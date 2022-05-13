package com.example.weather.helper.view_models

import androidx.lifecycle.*
import com.example.weather.data.data_providers.ForecastRepository
import com.example.weather.data.models.ForecastModel
import com.ibm.icu.text.RuleBasedNumberFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ForecastsViewModel @Inject constructor(
    forecastRepository: ForecastRepository
) : ViewModel(){

    val currentForecast = forecastRepository.getCurrentForecast().asLiveData()

    val forecasts = forecastRepository.getForecast().asLiveData()

    fun getCurrentDateItem(): List<ForecastModel>{
        return listOf(ForecastModel(date = "Today", day = null, night = null))
    }

    fun convertIntoWords(str: Double, language: String, Country: String): String? {
        val local = Locale(language, Country)
        val ruleBasedNumberFormat = RuleBasedNumberFormat(local, RuleBasedNumberFormat.SPELLOUT)
        return ruleBasedNumberFormat.format(str)
    }
}
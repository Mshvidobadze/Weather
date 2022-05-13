package com.example.weather.helper.util

import androidx.room.TypeConverter
import com.example.weather.data.models.ForecastModel
import com.example.weather.data.models.ObservationModel
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromForecastModelList(forecastModel: List<ForecastModel?>?): String? {
        if (forecastModel == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastModel?>?>() {}.type
        return gson.toJson(forecastModel, type)
    }

    @TypeConverter
    fun toForecastModelList(forecastModel: String?): List<ForecastModel>? {
        if (forecastModel == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastModel?>?>() {}.type
        return gson.fromJson<List<ForecastModel>>(forecastModel, type)
    }

    @TypeConverter
    fun fromCurrentForecastModelList(observationModel: List<ObservationModel?>?): String? {
        if (observationModel == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ObservationModel?>?>() {}.type
        return gson.toJson(observationModel, type)
    }

    @TypeConverter
    fun toCurrentForecastModelList(observationModel: String?): List<ObservationModel>? {
        if (observationModel == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ObservationModel?>?>() {}.type
        return gson.fromJson<List<ObservationModel>>(observationModel, type)
    }
}